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
	@Column(name="klinika_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "klinickiCentar_id")
	private KlinickiCentar klinickiCentar;
	
	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<AdminKlinike> admini;
	
	@OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
	private Set<Odsustvo> odsustva;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinskoOsoblje> osoblje;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Popust> popusti;
	
	
	@OneToOne
	@JoinColumn(name = "cijena_u_klinika_id" )
	private Cenovnik cenovnik;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> operacije;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TipPregleda> tipoviPregleda;
	
	
	@OneToMany(mappedBy = "klinika",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Sala> sale;

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
}
