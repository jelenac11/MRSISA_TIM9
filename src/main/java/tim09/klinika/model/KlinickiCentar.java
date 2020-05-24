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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class KlinickiCentar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "klinickiCentar_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "naziv", nullable = false, unique = true)
	private String naziv;

	@OneToMany(mappedBy = "klinickiCentar", cascade = CascadeType.ALL)
	private Set<Korisnik> korisnici;

	@OneToMany(mappedBy = "klinickiCentar", cascade = CascadeType.ALL)
	private Set<Klinika> klinike;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "lekovi_id")
	private Sifrarnik lekovi;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "dijagnoze_id")
	private Sifrarnik dijagnoze;

	public KlinickiCentar() {

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

	public Set<Korisnik> getkorisnici() {
		return korisnici;
	}

	public void setkorisnici(Set<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public Set<Klinika> getKlinike() {
		return klinike;
	}

	public void setKlinike(Set<Klinika> klinike) {
		this.klinike = klinike;
	}

	public Sifrarnik getLekovi() {
		return lekovi;
	}

	public void setLekovi(Sifrarnik lekovi) {
		this.lekovi = lekovi;
	}

	public Sifrarnik getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(Sifrarnik dijagnoze) {
		this.dijagnoze = dijagnoze;
	}
}
