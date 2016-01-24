package pl.fawltytowers.beans;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import pl.fawltytowers.model.Address;
import pl.fawltytowers.model.Hotel;
import pl.fawltytowers.model.Location;
import pl.fawltytowers.model.Room;
import pl.fawltytowers.model.RoomFactory;
import pl.fawltytowers.model.WindowView;

/**
 * Create initial database state for tests and first run of web server. 
 * 
 * @author krzysztof bujanecki
 *
 */
@Singleton
public class InitData {
	
	final static Logger logger = Logger.getLogger(InitData.class);
	
	@Inject
	private EntityManagerProvider entityManagerProvider;

	public void clearData() {
		EntityManagerFactory emf = entityManagerProvider.getEmf();
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.createQuery("delete from Hotel").executeUpdate();
			em.createQuery("delete from Location").executeUpdate();
			em.createQuery("delete from Room").executeUpdate();
			em.createQuery("delete from Reservation").executeUpdate();
			em.createQuery("delete from Guest").executeUpdate();
			em.getTransaction().commit();
		} catch (PersistenceException px) {
			logger.warn("=======> First ever run");
		} finally {
			em.close();
		}
	}

	public Hotel createFawltyTowersIfNotExists() {
		EntityManagerFactory emf = entityManagerProvider.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		String name = "Fawlty Towers";
		Hotel hotel = getHotel(em, name);
		if (hotel != null) {
			return hotel;
		}
		hotel = new Hotel("Fawlty Towers");
		em.persist(hotel);
		Address address = new Address("Torquay", "Torbay", "01803", "16 Elwood Avenue");
		Location location = new Location();
		location.setAddress(address);
		em.persist(location);
		Room northRoom = RoomFactory.createStandardRoom(hotel, WindowView.NORTH);
		em.persist(northRoom);
		Room southRoom = RoomFactory.createStandardRoom(hotel, WindowView.SOUTH);
		em.persist(southRoom);
		Room westRoom = RoomFactory.createSpecialRoom(hotel, WindowView.WEST);
		em.persist(westRoom);
		Room eastRoom = RoomFactory.createExclusiveRoom(hotel, WindowView.EAST);
		em.persist(eastRoom);
			
		em.getTransaction().commit();
		em.close();
		
		return hotel;
	}
	

	private Hotel getHotel(EntityManager em, String name) {
		try{
			Query query = em.createQuery("select h from Hotel h where h.name = :name");
			query.setParameter("name", "Fawlty Towers");
			return (Hotel) query.getSingleResult();
		}catch (Exception ex){
			return null;
		}
	}

}
