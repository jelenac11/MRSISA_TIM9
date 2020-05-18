package tim09.klinika.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Pregled {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pregled_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lekar_id")
	private Lekar lekar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pacijent_id")
	private Pacijent pacijent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipPregleda_id")
	private TipPregleda tipPregleda;

	@OneToOne(mappedBy = "pregled", fetch = FetchType.LAZY)
	private Izvestaj izvestaj;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala_id")
	private Sala sala;

	@Column(name = "vreme")
	private long vreme;

	@Column(name = "trajanje")
	private int trajanje;

	@Column(name = "otkazan")
	private boolean otkazan;
	
	@Column(name = "zauzet")
	private boolean zauzet;
	
	@Column(name = "potvrdjen")
	private boolean potvrdjen;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;

	public Pregled() {

	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Izvestaj getIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(Izvestaj izvestaj) {
		this.izvestaj = izvestaj;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getVreme() {
		return vreme;
	}

	public void setVreme(long vreme) {
		this.vreme = vreme;
	}

	public boolean isOtkazan() {
		return otkazan;
	}

	public void setOtkazan(boolean otkazan) {
		this.otkazan = otkazan;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public boolean isZauzet() {
		return zauzet;
	}

	public void setZauzet(boolean zauzet) {
		this.zauzet = zauzet;
	}

	public boolean isPotvrdjen() {
		return potvrdjen;
	}

	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
}
