package pl.fawltytowers.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.fawltytowers.model.Guest;
import pl.fawltytowers.model.Hotel;
import pl.fawltytowers.model.Reservation;
import pl.fawltytowers.model.Room;
import pl.fawltytowers.util.ReservationPredicate;

/**
 * Main service facade, that provide data to view layer. Every method is call in
 * new transaction {@link Tx}, {@link TxInterceptor}
 * 
 * @author krzysztof bujanecki
 *
 */
@Tx
@RequestScoped
public class HotelService {

	public HotelService() {
	}

	@Inject
	@EManager
	private EntityManager em;

	/**
	 * Find hotel by name
	 * 
	 * @param name
	 *            - hotel name
	 * @return hotel entity
	 */
	public Hotel findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Hotel> query = cb.createQuery(Hotel.class);
		Root<Hotel> root = query.from(Hotel.class);
		ParameterExpression<String> parameter = cb.parameter(String.class);
		query.select(root).where(cb.equal(root.get("name"), parameter));
		TypedQuery<Hotel> createQuery = em.createQuery(query);
		createQuery.setParameter(parameter, name);

		return createQuery.getSingleResult();

	}

	/**
	 * Find rooms related to hotel with this id.
	 * 
	 * @param hotelId
	 *            - hotel primary key
	 * @return related rooms
	 */
	public List<Room> findRooms(Long hotelId) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Room> query = cb.createQuery(Room.class);
		Root<Room> root = query.from(Room.class);
		Join<Room, Hotel> join = root.join("hotel");
		Predicate predicate = cb.equal(join.get("id"), hotelId);

		query.select(root).where(predicate);
		TypedQuery<Room> createQuery = em.createQuery(query);
		return createQuery.getResultList();
	}

	/**
	 * Find room available for reservation
	 * 
	 * @param hotelId
	 *            - related hotel
	 * @param from
	 *            - check in date
	 * @param to
	 *            - check out date
	 * @return available rooms
	 */
	public List<Room> findAvailableRooms(Long hotelId, Date from, Date to) {
		List<Room> findRooms = this.findRooms(hotelId);
		List<Room> result = new ArrayList<>();
		for (Room room : findRooms) {
			List<Reservation> reservations = room.getReservations();
			if (reservations == null || reservations.isEmpty()) {
				result.add(room);
			} else if (!reservations.stream().anyMatch(new ReservationPredicate(from, to))) {
				result.add(room);
			}
		}

		return result;
	}

	/**
	 * Find all reservations related to hotel primary key 
	 * 
	 * @param hotelId - related hotel primary key
	 * @return all reservations related to hotel
	 */
	public List<Reservation> findHotelReservations(Long hotelId) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
		Root<Reservation> root = query.from(Reservation.class);
		Join<Reservation, Room> joinRoom = root.join("room");
		Join<Room, Hotel> joinHotel = joinRoom.join("hotel");
		Predicate predicate = cb.equal(joinHotel.get("id"), hotelId);

		query.select(root).where(predicate);
		TypedQuery<Reservation> createQuery = em.createQuery(query);

		return createQuery.getResultList();

	}

	/**
	 * 
	 * Create reservation for existing room and new guest object. 
	 * 
	 * @param room - existing room
	 * @param guest - new guest object
	 * @param from - check in date
	 * @param to - check out date
	 * @return persisted reservation 
	 */
	public Reservation creteRoomReservation(Room room, Guest guest, Date from, Date to) {

		Reservation reservation = new Reservation();
		reservation.setCancel(false);
		reservation.setFrom(from);
		reservation.setTo(to);
		reservation.setGuest(guest);
		reservation.setRoom(room);
		em.persist(guest);
		em.persist(reservation);
		return reservation;
	}

	/**
	 * Update reservation
	 *  
	 * @param reservation - reservation
	 * @return merged reservation
	 */
	public Reservation updateReservation(Reservation reservation) {
		return em.merge(reservation);
	}

}
