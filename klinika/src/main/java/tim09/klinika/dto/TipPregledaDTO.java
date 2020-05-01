package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private StavkaCenovnikaDTO stavkaCenovnika;
	private String klinika;
	private ArrayList<LekarDTO> lekari;
	private ArrayList<PregledDTO> pregledi;

	public TipPregledaDTO() {

	}

	public TipPregledaDTO(TipPregleda os) {
		// TODO Auto-generated constructor stub
		this.id=os.getId();
		this.naziv=os.getNaziv();
		this.opis=os.getOpis();
		this.klinika=os.getKlinika().getNaziv();
	}
	
	public TipPregledaDTO(long id,String naziv,String opis) {
		this.id=id;
		this.naziv=naziv;
		this.opis=opis;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public StavkaCenovnikaDTO getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnikaDTO stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public String getKlinika() {
		return klinika;
	}

	public void setKlinika(String klinika) {
		this.klinika = klinika;
	}

	public ArrayList<LekarDTO> getLekari() {
		return lekari;
	}

	public void setLekari(ArrayList<LekarDTO> lekari) {
		this.lekari = lekari;
	}

	public ArrayList<PregledDTO> getPregledi() {
		return pregledi;
	}

	public void setPregledi(ArrayList<PregledDTO> pregledi) {
		this.pregledi = pregledi;
	}

}
