package pl.fawltytowers.util;

import java.util.Date;

import pl.fawltytowers.model.Reservation;

 
/**
 * 
 * Predicate that returns true if tested object is not cancelled or
 * reservation have covered dates.
 * 
 * @author krzysztof bujanecki
 *
 */
public class ReservationPredicate implements java.util.function.Predicate<Reservation> {
	private Date from;
	private Date to;

	public ReservationPredicate(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean test(Reservation t) {

		if (t.getCancel() == true) {
			return false;
		}
		return to.compareTo(t.getFrom()) >= 0 && from.compareTo(t.getTo()) <= 0;

	}

}