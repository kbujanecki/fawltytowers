package pl.fawltytowers.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Main project utility class.
 * 
 * @author krzysztof bujanecki
 *
 */
public class Util {
	
	/**
	 *  Create date, base on {@link LocalDate}
	 *  
	 * @param year
	 * @param month
	 * @param day
	 * @return date
	 */
	public static Date toDate(int year, int month, int day) {
		
		Instant instant = LocalDate.of(year, month, day).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
}
