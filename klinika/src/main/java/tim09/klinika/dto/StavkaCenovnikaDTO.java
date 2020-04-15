package tim09.klinika.dto;

import java.util.ArrayList;

public class StavkaCenovnikaDTO {

	private Long id;
	private TipPregledaDTO tipPregleda;
	private double cena;
	private ArrayList<PopustDTO> popusti;

	public StavkaCenovnikaDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipPregledaDTO getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregledaDTO tipPregleda) {
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
