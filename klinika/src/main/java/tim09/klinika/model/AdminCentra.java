package tim09.klinika.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("AC")
public class AdminCentra extends Korisnik {
	
	public AdminCentra() {

	}

}
