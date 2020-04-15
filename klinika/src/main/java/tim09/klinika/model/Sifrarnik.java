package tim09.klinika.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Sifrarnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	@JoinColumn(name = "sifrarnik_id" )
	private Set<StavkaSifrarnika> stavke;

	public Sifrarnik() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<StavkaSifrarnika> getStavke() {
		return stavke;
	}

	public void setStavke(Set<StavkaSifrarnika> stavke) {
		this.stavke = stavke;
	}
	
}
