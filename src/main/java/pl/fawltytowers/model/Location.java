package pl.fawltytowers.model;

import java.io.Serializable;

import javax.jdo.annotations.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 77849445336738575L;
	@Id
	@GeneratedValue
	Long id;

	@OneToOne
	private Hotel hotel;

	@Embedded
	private Address address;

	public Location() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}