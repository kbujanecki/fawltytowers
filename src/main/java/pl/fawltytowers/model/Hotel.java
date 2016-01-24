package pl.fawltytowers.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name = Hotel.QUERY_FIND_BY_NAME, query = "select h from Hotel h where h.name = :name")
public class Hotel implements Serializable {

	public static final String QUERY_FIND_BY_NAME = "findByName";
	/**
	 * 
	 */
	private static final long serialVersionUID = 77849445336738575L;
	@Id
	@GeneratedValue
	Long id;

	private String name;

	@OneToOne(mappedBy = "hotel")
	private Location location;

	@OneToMany(mappedBy = "hotel")
	private List<Room> rooms;

	public Hotel() {
	}

	public Long getId() {
		return id;
	}
	
	public Hotel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}