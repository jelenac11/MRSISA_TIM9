package tim09.klinika.model;


import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Izvestaj {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="opis",unique = false,nullable = true)
	private String opis;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="izvestaj_id")
	private Pregled pregled;
	
	@ManyToOne
	private StavkaSifrarnika dijagnoza;
	
	@OneToMany
	@JoinColumn(name = "izvestaj_id" )
	private Set<Recept> recepti;

	public Izvestaj() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public StavkaSifrarnika getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(StavkaSifrarnika dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}
}
