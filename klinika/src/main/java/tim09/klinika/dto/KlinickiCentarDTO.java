package tim09.klinika.dto;

import java.util.ArrayList;

public class KlinickiCentarDTO {

	private Long id;
	private String naziv;
	private ArrayList<KorisnikDTO> korisnici;
	private ArrayList<KlinikaDTO> klinike;
	private SifrarnikDTO lekovi;
	private SifrarnikDTO dijagnoze;

	public KlinickiCentarDTO() {

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

	public ArrayList<KorisnikDTO> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<KorisnikDTO> korisnici) {
		this.korisnici = korisnici;
	}

	public ArrayList<KlinikaDTO> getKlinike() {
		return klinike;
	}

	public void setKlinike(ArrayList<KlinikaDTO> klinike) {
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
