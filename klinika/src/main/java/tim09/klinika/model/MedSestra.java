package tim09.klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("MS")
public class MedSestra extends MedicinskoOsoblje {

	@OneToMany(mappedBy = "medSestra", cascade = CascadeType.ALL)
	private Set<Recept> recepti;
	
	public MedSestra() {

	}
}
