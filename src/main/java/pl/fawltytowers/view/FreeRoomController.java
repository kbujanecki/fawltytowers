package pl.fawltytowers.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.fawltytowers.beans.HotelService;
import pl.fawltytowers.model.Guest;
import pl.fawltytowers.model.Room;

/**
 * Controller for freerooms.xhmtl view. 
 * 
 * @author krzysztof bujanecki
 *
 */
@Named("freeRoomController")
@SessionScoped
public class FreeRoomController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074583290089635174L;

	private Date from;

	private Date to;

	private Room chosenOne;

	private Guest guest;

	private List<Room> rooms;

	private @Inject HotelService hotelService;

	private @Inject @HotelId Long hotelId;

	@PostConstruct
	public void init() {

		this.search();
	}

	public void clear() {
		from = null;
		to = null;
		rooms = null;
		guest = null;
		chosenOne = null;
	}

	public void search() {
		if (from != null && to != null) {
			this.rooms = hotelService.findAvailableRooms(hotelId, from, to);
		}
	}

	public void add(Room room) {
		this.chosenOne = room;
		this.guest = new Guest();
	}

	public void createReservation() {

		this.hotelService.creteRoomReservation(chosenOne, guest, from, to);

		this.chosenOne = null;
		this.guest = null;

		this.search();
	}

	public void cancel() {
		this.chosenOne = null;
		this.guest = null;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Room getChosenOne() {
		return chosenOne;
	}

	public Guest getGuest() {
		return guest;
	}
}
