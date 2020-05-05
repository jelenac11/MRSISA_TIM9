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

@Entity
public class Klinika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "klinika_id")
	private Long id;

	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "lokacija", nullable = false)
	private String lokacija;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<AdminKlinike> admini;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Odsustvo> odsustva;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<MedicinskoOsoblje> osoblje;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Popust> popusti;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cenovnik_id")
	private Cenovnik cenovnik;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Operacija> operacije;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<TipPregleda> tipoviPregleda;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Sala> sale;

	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<OcenaKlinike> ocene;
	
	@Column(name = "prosecna_ocena")
	private double prosecnaOcena;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinickiCentar_id")
	private KlinickiCentar klinickiCentar;

	public Klinika() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<AdminKlinike> getAdmini() {
		return admini;
	}

	public void setAdmini(Set<AdminKlinike> admini) {
		this.admini = admini;
	}

	public Set<Odsustvo> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(Set<Odsustvo> odsustva) {
		this.odsustva = odsustva;
	}

	public Set<MedicinskoOsoblje> getOsoblje() {
		return osoblje;
	}

	public void setOsoblje(Set<MedicinskoOsoblje> osoblje) {
		this.osoblje = osoblje;
	}

	public Set<Popust> getPopusti() {
		return popusti;
	}

	public void setPopusti(Set<Popust> popusti) {
		this.popusti = popusti;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Set<TipPregleda> getTipoviPregleda() {
		return tipoviPregleda;
	}

	public void setTipoviPregleda(Set<TipPregleda> tipoviPregleda) {
		this.tipoviPregleda = tipoviPregleda;
	}

	public Set<Sala> getSale() {
		return sale;
	}

	public void setSale(Set<Sala> sale) {
		this.sale = sale;
	}

	public Set<OcenaKlinike> getOcene() {
		return ocene;
	}

	public void setOcene(Set<OcenaKlinike> ocene) {
		this.ocene = ocene;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
}
