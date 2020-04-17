package tim09.klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TipSifre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tipSifre_id")
	private Long id;
	
	@Column(name="naziv",nullable = false,unique = true)
	private String naziv;
	
	@OneToMany(mappedBy = "tipSifre",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<StavkaSifrarnika> stavke;

	public TipSifre() {
		super();
	}

	public TipSifre(Long id, String naziv, Set<StavkaSifrarnika> stavke) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.stavke = stavke;
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

	public Set<StavkaSifrarnika> getStrucnjaci() {
		return stavke;
	}

	public void setStrucnjaci(Set<StavkaSifrarnika> stavke) {
		this.stavke = stavke;
	} 
	
	
}
