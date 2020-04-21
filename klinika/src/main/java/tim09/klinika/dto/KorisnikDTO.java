package tim09.klinika.dto;

import tim09.klinika.model.Korisnik;

public class KorisnikDTO {

	private Long id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;

	public KorisnikDTO() {

	}

	public KorisnikDTO(Korisnik k) {
		this.id = k.getId();
		this.email = k.getEmail();
		this.lozinka = k.getLozinka();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.adresa = k.getAdresa();
		this.grad = k.getGrad();
		this.drzava = k.getDrzava();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

}
