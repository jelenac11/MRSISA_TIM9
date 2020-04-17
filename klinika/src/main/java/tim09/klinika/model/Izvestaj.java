package tim09.klinika.model;


import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Izvestaj {

	@Id
    @Column(name="izvestaj_id")
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign",parameters=@Parameter(name="property", value="pregled"))
    private Long id;
	
	@Column(name="opis",unique = false,nullable = true)
	private String opis;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Pregled pregled;
	
	@ManyToOne
	@JoinColumn(name="stavkaSifrarnika_id")
	private StavkaSifrarnika dijagnoza;
	
	@OneToMany(mappedBy = "izvestaj", cascade = CascadeType.ALL)
	private Set<Recept> recepti;

	@ManyToOne
    @JoinColumn(name = "zdravstveniKarton_id")
	private ZdravstveniKarton zdravstveniKarton;
	
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
