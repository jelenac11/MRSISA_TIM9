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

import tim09.klinika.model.TipSifre;

@Entity
public class StavkaSifrarnika {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="stavkaSifrarnika_id")
	private Long id;
	
	@Column(name = "sifra",unique = true,nullable = false)
	private String sifra;
	
	@Column(name = "naziv",nullable = false)
	private String naziv;
	
	@ManyToOne
	@JoinColumn(name="tipSifre_id")
	private TipSifre tipSifre;
	
	@OneToMany(mappedBy = "dijagnoza", cascade = CascadeType.ALL)
	private Set<Izvestaj> izvestaji;
	
	@OneToMany(mappedBy = "lek", cascade = CascadeType.ALL)
    private Set<Recept> recepti;
	
	@ManyToOne
	@JoinColumn(name="sifrarnik_id")
	private Sifrarnik sifrarnik;
	
	

	public StavkaSifrarnika() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipSifre getTipSifre() {
		return tipSifre;
	}

	public void setTipSifre(TipSifre tipSifre) {
		this.tipSifre = tipSifre;
	}
}
