package pl.fawltytowers.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ST")
public class StandardRoom extends Room {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9213073203584338578L;

}
