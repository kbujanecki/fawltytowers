package pl.fawltytowers.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.fawltytowers.beans.HotelService;
import pl.fawltytowers.model.Reservation;

/**
 * Controller for reservations.xhmtl view. 
 * 
 * @author krzysztof bujanecki
 *
 */
@Named("reservationController")
@SessionScoped
public class ReservationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074583290089635174L;

	private List<Reservation> reservations;

	private @Inject HotelService hotelService;

	private @Inject @HotelId Long hotelId;

	@PostConstruct
	public void init() {

		this.search();
	}

	public void search() {

		this.reservations = hotelService.findHotelReservations(hotelId);
	}

	public void cancel(Reservation reservation) {
		reservation.setCancel(true);
		this.hotelService.updateReservation(reservation);
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
