package klinika.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AC")
public class AdminCentra extends Korisnik {

	private static final long serialVersionUID = 1L;

	public AdminCentra() {

	}

}
