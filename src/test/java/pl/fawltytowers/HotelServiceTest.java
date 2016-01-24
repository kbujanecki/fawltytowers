package pl.fawltytowers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.jglue.cdiunit.InRequestScope;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.fawltytowers.beans.EntityManagerProvider;
import pl.fawltytowers.beans.HotelService;
import pl.fawltytowers.beans.InitData;
import pl.fawltytowers.beans.Tx;
import pl.fawltytowers.beans.TxInterceptor;
import pl.fawltytowers.model.Guest;
import pl.fawltytowers.model.Hotel;
import pl.fawltytowers.model.Reservation;
import pl.fawltytowers.model.Room;
import pl.fawltytowers.util.Util;

@RunWith(CdiRunner.class)
@AdditionalClasses(value = { EntityManagerProvider.class, Tx.class, TxInterceptor.class })
public class HotelServiceTest {

	final static Logger logger = Logger.getLogger(HotelServiceTest.class);

	@Inject
	ContextController contextController;

	@Inject
	private HotelService hotelService;

	@Inject
	private InitData initData;

	private Hotel fawlty;

	Date from = Util.toDate(2016, 1, 1);

	Date to = Util.toDate(2016, 1, 10);

	@Before
	public void before() {
		initData.clearData();
		fawlty = initData.createFawltyTowersIfNotExists();
	}

	/**
	 * Strange {@link CdiRunner} behave:
	 * 
	 * https://groups.google.com/forum/#!topic/cdi-unit/2e-XnyduXcs
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

		try {
			InitialContext initialContext = new InitialContext();
			initialContext.unbind("java:comp/BeanManager");

		} catch (NamingException ne) {
			System.out.println("unbind: " + ne);
			ne.printStackTrace();
		}
	}

	@Test
	@InRequestScope
	public void findHotel() {
		Hotel hotel = hotelService.findByName("Fawlty Towers");
		Assert.assertNotNull(hotel);
	}

	@Test
	@InRequestScope
	public void findRooms() {
		List<Room> rooms = hotelService.findRooms(fawlty.getId());
		Assert.assertNotNull(rooms);
		Assert.assertFalse(rooms.isEmpty());

	}

	@Test
	@InRequestScope
	public void findAvailableRooms() {

		List<Room> rooms = hotelService.findAvailableRooms(fawlty.getId(), from, to);
		Assert.assertNotNull(rooms);
		Assert.assertFalse(rooms.isEmpty());

	}

	@Test
	public void bookRoom() {
		contextController.openRequest();
		List<Room> rooms = hotelService.findAvailableRooms(fawlty.getId(), from, to);
		Assert.assertNotNull(rooms);
		Assert.assertFalse(rooms.isEmpty());
		contextController.closeRequest();
		contextController.openRequest();
		Room room = rooms.get(0);
		Guest guest = new Guest("Mr. Smith");
		Reservation reservation = hotelService.creteRoomReservation(room, guest, from, to);
		contextController.closeRequest();
		Assert.assertNotNull(reservation);
		Assert.assertNotNull(reservation.getId());
		Assert.assertNotNull(guest.getId());

		contextController.openRequest();
		List<Room> avrooms = hotelService.findAvailableRooms(fawlty.getId(), from, to);
		Assert.assertNotNull(avrooms);
		Assert.assertFalse(avrooms.isEmpty());
		Assert.assertTrue(avrooms.size() + 1 == rooms.size());
		contextController.closeRequest();
	}

	@Test
	public void findHotelReservations() {
		contextController.openRequest();
		List<Room> rooms = hotelService.findAvailableRooms(fawlty.getId(), from, to);
		Assert.assertNotNull(rooms);
		Assert.assertFalse(rooms.isEmpty());
		contextController.closeRequest();
		contextController.openRequest();
		Room room = rooms.get(0);
		Guest guest = new Guest("Mr. Smith");
		Reservation reservation = hotelService.creteRoomReservation(room, guest, from, to);
		Assert.assertNotNull(reservation);
		contextController.closeRequest();
		contextController.openRequest();
		List<Reservation> loaded = hotelService.findHotelReservations(fawlty.getId());
		Assert.assertNotNull(reservation);
		Assert.assertEquals(reservation.getId(), loaded.get(0).getId());
		contextController.closeRequest();
	}

}
