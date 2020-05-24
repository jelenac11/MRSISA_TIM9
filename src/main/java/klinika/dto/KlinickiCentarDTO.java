package klinika.dto;

import java.util.List;

public class KlinickiCentarDTO {

	private Long id;
	private String naziv;
	private List<KorisnikDTO> korisnici;
	private List<KlinikaDTO> klinike;
	private SifrarnikDTO lekovi;
	private SifrarnikDTO dijagnoze;

	public KlinickiCentarDTO() {
		super();
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

	public List<KorisnikDTO> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<KorisnikDTO> korisnici) {
		this.korisnici = korisnici;
	}

	public List<KlinikaDTO> getKlinike() {
		return klinike;
	}

	public void setKlinike(List<KlinikaDTO> klinike) {
		this.klinike = klinike;
	}

	public SifrarnikDTO getLekovi() {
		return lekovi;
	}

	public void setLekovi(SifrarnikDTO lekovi) {
		this.lekovi = lekovi;
	}

	public SifrarnikDTO getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(SifrarnikDTO dijagnoze) {
		this.dijagnoze = dijagnoze;
	}
}
