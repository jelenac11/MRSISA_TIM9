package tim09.klinika.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("AC")
public class AdminCentra extends Korisnik {

	public AdminCentra() {

	}

}
