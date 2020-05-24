package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Popust;
import klinika.model.StavkaCenovnika;

public class StavkaCenovnikaDTO {

	private Long id;
	private String tipPregleda;
	private double cena;
	private List<PopustDTO> popusti;

	public StavkaCenovnikaDTO() {

	}

	public StavkaCenovnikaDTO(StavkaCenovnika sc) {
		this.id = sc.getId();
		this.tipPregleda = sc.getTipPregleda().getNaziv();
		this.cena = sc.getCena();
		this.popusti = new ArrayList<>();
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

	public List<PopustDTO> getPopusti() {
		return popusti;
	}

	public void setPopusti(List<PopustDTO> popusti) {
		this.popusti = popusti;
	}

}
