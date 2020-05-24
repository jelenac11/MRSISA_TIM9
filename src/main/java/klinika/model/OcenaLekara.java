package klinika.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("OL")
public class OcenaLekara extends Ocena {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lekar_id")
	private Lekar lekar;

	public OcenaLekara() {
		super();
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
}
