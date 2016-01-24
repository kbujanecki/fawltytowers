	package pl.fawltytowers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.fawltytowers.model.Reservation;
import pl.fawltytowers.util.ReservationPredicate;

public class ReservationPredicateTest {
	
	private Reservation reservation;
	
	
	@Before
	public void before(){
		reservation = new Reservation();
		reservation.setCancel(false);
		reservation.setFrom(toDate(2016, 1, 1));
		reservation.setTo(toDate(2016, 1, 10));
	}
	
	
	@Test
	public void in(){
		Date from = toDate(2016, 1, 1);
		Date to = toDate(2016, 1, 9);
		Assert.assertTrue(new ReservationPredicate(from, to).test(this.reservation));
	}
	
	@Test
	public void inHalfDown(){
		Date from = toDate(2015, 1, 1);
		Date to = toDate(2016, 1, 5);
		Assert.assertTrue(new ReservationPredicate(from, to).test(this.reservation));
	}
	
	@Test
	public void inHalfUp(){
		Date from = toDate(2016, 1, 5);
		Date to = toDate(2016, 1, 20);
		Assert.assertTrue(new ReservationPredicate(from, to).test(this.reservation));
	}
	
	@Test
	public void outDown(){
		Date from = toDate(2015, 1, 5);
		Date to = toDate(2015, 1, 20);
		Assert.assertFalse(new ReservationPredicate(from, to).test(this.reservation));
	}
	
	@Test
	public void outUp(){
		Date from = toDate(2016, 1, 11);
		Date to = toDate(2016, 1, 20);
		Assert.assertFalse(new ReservationPredicate(from, to).test(this.reservation));
	}

	public static Date toDate(int year, int month, int day) {
		Instant instant = LocalDate.of(year, month, day).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
}
