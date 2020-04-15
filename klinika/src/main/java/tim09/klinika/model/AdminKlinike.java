package tim09.klinika.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("AK")
public class AdminKlinike extends Korisnik {
	
	@ManyToOne(cascade =CascadeType.ALL,fetch = FetchType.LAZY )
	private Klinika klinika;

	public AdminKlinike() {

	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
}
