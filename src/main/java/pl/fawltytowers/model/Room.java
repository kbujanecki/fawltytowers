package pl.fawltytowers.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance
public abstract class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 77849445336738575L;
	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false)
	private WindowView windowView;

	@ManyToOne
	private Hotel hotel;

	@OneToMany(mappedBy = "room")
	private List<Reservation> reservations;

	public Room() {
	}

	public Long getId() {
		return id;
	}

	public WindowView getWindowView() {
		return windowView;
	}

	public void setWindowView(WindowView windowView) {
		this.windowView = windowView;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {

		return this.getId() + " " + this.getClass().getSimpleName();
	}
}