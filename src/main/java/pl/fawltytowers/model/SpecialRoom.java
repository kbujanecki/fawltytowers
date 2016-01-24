package pl.fawltytowers.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SP")
public class SpecialRoom extends Room {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9213073203584338578L;

}
