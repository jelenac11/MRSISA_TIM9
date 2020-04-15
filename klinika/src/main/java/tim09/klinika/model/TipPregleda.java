package tim09.klinika.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


@Entity
public class TipPregleda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="naziv",nullable = false,unique = true)
	private String naziv;
	
	@Column(name="opis",nullable = true,unique = false)
	private String opis;
	
	@OneToOne(mappedBy = "tipPregleda",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private StavkaCenovnika stavkaCenovnika;

	@ManyToMany(mappedBy = "specijalnosti")
	private Set<Lekar> strucnjaci; 
	
	public TipPregleda() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public StavkaCenovnika getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnika stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}
}
