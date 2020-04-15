package tim09.klinika.dto;

public class StavkaSifrarnikaDTO {

	private Long id;
	private String sifra;
	private String naziv;
	private TipSifre tipSifre;

	public StavkaSifrarnikaDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipSifre getTipSifre() {
		return tipSifre;
	}

	public void setTipSifre(TipSifre tipSifre) {
		this.tipSifre = tipSifre;
	}
}
