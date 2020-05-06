package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Popust;
import tim09.klinika.model.StavkaCenovnika;

public class StavkaCenovnikaDTO {

	private Long id;
	private String tipPregleda;
	private double cena;
	private ArrayList<PopustDTO> popusti;

	public StavkaCenovnikaDTO() {

	}
	
	public StavkaCenovnikaDTO(StavkaCenovnika sc) {
		this.id = sc.getId();
		this.tipPregleda = sc.getTipPregleda().getNaziv();
		this.cena = sc.getCena();
		this.popusti = new ArrayList<PopustDTO>();
		for (Popust pop : sc.getPopusti()) {
			this.popusti.add(new PopustDTO(pop));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public ArrayList<PopustDTO> getPopusti() {
		return popusti;
	}

	public void setPopusti(ArrayList<PopustDTO> popusti) {
		this.popusti = popusti;
	}

}
