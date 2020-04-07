package tim09.klinika.model;

public class Pacijent {
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brOsiguranika;
	
	public Pacijent() {
		
	}
	
	public Pacijent(String _email, String loz, String _ime, String prz, String _adresa, String _grad, String _drzava, String br) {
		this.email = _email;
		this.lozinka = loz;
		this.ime = _ime;
		this.prezime = prz;
		this.adresa = _adresa;
		this.grad = _grad;
		this.drzava = _drzava;
		this.brOsiguranika = br;
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

	public String getBrOsiguranika() {
		return brOsiguranika;
	}

	public void setBrOsiguranika(String brOsiguranika) {
		this.brOsiguranika = brOsiguranika;
	}
}
