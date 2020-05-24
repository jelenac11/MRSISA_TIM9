package klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("MS")
public class MedSestra extends MedicinskoOsoblje {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "medSestra", cascade = CascadeType.ALL)
	private Set<Recept> recepti;

	public MedSestra() {
		super();
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

}
