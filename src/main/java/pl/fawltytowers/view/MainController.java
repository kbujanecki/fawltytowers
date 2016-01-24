package pl.fawltytowers.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pl.fawltytowers.beans.HotelService;
import pl.fawltytowers.model.Hotel;

/**
 *  Main view controller with page flow methods.
 * 
 * @author krzysztof bujaneceki
 *
 */
@Named("mainController")
@SessionScoped
public class MainController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074583290089635174L;

	private String baseUrl = null;

	private String freeRooms = null;

	private String reservations = null;

	private Hotel hotel;

	private @Inject HotelService hotelService;

	private @Inject ReservationController reservationController;

	private @Inject FreeRoomController freeRoomController;
	
	@PostConstruct
	public void init() {
		hotel = hotelService.findByName("Fawlty Towers");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String baseUrl = externalContext.getInitParameter("BaseUrl");

		this.baseUrl = baseUrl;
		this.freeRooms = baseUrl + "/freerooms.xhtml";
		this.reservations = baseUrl + "/reservations.xhtml";
	}

	@Produces
	@HotelId
	public Long getHotelId(){
		return this.hotel.getId();
	}

	public Hotel getHotel() {
		return hotel;
	}

	public String processFreerooms() {
		this.freeRoomController.clear();
		return "freerooms";
	}

	public String processReservations() {
		this.reservationController.search();
		return "reservations";
	}

	public String getFreeRooms() {
		return freeRooms;
	}

	public String getReservations() {
		return reservations;
	}

	public String getBaseUrl() {
		return baseUrl;
	}
}
