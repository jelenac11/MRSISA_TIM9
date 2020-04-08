package tim09.klinika.model;

public class MedicinskoOsoblje {

	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	public MedicinskoOsoblje() {
		super();
	}
	public MedicinskoOsoblje(String email, String lozinka, String ime, String prezime, String adresa, String grad,
			String drzava) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
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
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		MedicinskoOsoblje osoba=(MedicinskoOsoblje) obj;
		if(osoba.email.equals(this.email) && osoba.lozinka.equals(this.lozinka) && osoba.ime.equals(this.ime)) {
			return true;
		}
		return false;
	}
	
	
}
