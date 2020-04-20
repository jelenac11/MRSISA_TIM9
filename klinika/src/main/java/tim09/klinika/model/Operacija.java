package tim09.klinika.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Operacija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operacija_id")
	private Long id;

	@ManyToMany
	@JoinTable(name = "operisali", joinColumns = @JoinColumn(name = "operacija_id", referencedColumnName = "operacija_id"), inverseJoinColumns = @JoinColumn(name = "lekar_id", referencedColumnName = "korisnik_id"))
	private Set<Lekar> lekari;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pacijent_id")
	private Pacijent pacijent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala_id")
	private Sala sala;

	@Column(name = "vreme", nullable = false)
	private long vreme;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;

	public Operacija() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public long getVreme() {
		return vreme;
	}

	public void setVreme(long vreme) {
		this.vreme = vreme;
	}
}
