package tim09.klinika.dto;

import tim09.klinika.model.Klinika;

public class KlinikaDTO {

	private Long id;
	private double ocena;
	private String naziv;
	private String lokacija;
	private boolean zadovoljava;
	private double cena;
	private String opis;
	private double lat;
	private double lng;
	
	public KlinikaDTO() {

	}

	public KlinikaDTO(Klinika klinika) {
		this.naziv = klinika.getNaziv();
		this.id = klinika.getId();
		this.lokacija = klinika.getLokacija();
		this.ocena = klinika.getProsecnaOcena();
		this.zadovoljava = true;
		this.cena = 0;
		this.lat=klinika.getLat();
		this.lng=klinika.getLng();
		this.opis=klinika.getOpis();
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

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public boolean isZadovoljava() {
		return zadovoljava;
	}

	public void setZadovoljava(boolean zadovoljava) {
		this.zadovoljava = zadovoljava;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	

}
