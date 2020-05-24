package klinika.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("OK")
public class OcenaKlinike extends Ocena {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;

	public OcenaKlinike() {
		super();
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
}
