package klinika.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cenovnik implements Serializable {

	private static final long serialVersionUID = 8760048092432398247L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cenovnik_id", unique = true, nullable = false)
	private Long id;

	@OneToMany(mappedBy = "cenovnik", cascade = CascadeType.ALL)
	private Set<StavkaCenovnika> stavke;

	@OneToOne(mappedBy = "cenovnik", fetch = FetchType.LAZY)
	private Klinika klinika;

	public Cenovnik() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<StavkaCenovnika> getStavke() {
		return stavke;
	}

	public void setStavke(Set<StavkaCenovnika> stavke) {
		this.stavke = stavke;
	}
}
