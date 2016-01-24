package pl.fawltytowers.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EX")
public class ExclusiveRoom extends Room {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9213073203584338578L;

}
