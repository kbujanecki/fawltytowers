package pl.fawltytowers.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Guest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Long id;

	private String name;

	@OneToOne(mappedBy="guest")
	private Reservation reservation;

	public Guest() {
	}

	public Guest(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}