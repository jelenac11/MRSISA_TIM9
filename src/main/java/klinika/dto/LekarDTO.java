package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Lekar;
import klinika.model.TipPregleda;

public class LekarDTO extends MedicinskoOsobljeDTO {

	private List<TipPregledaDTO> specijalnosti;
	private double prosecnaOcena;
	private boolean slobodan;

	public LekarDTO() {

	}

	public LekarDTO(Lekar l) {
		super(l);
		if (l == null) {
			return;
		}
		this.specijalnosti = new ArrayList<>();
		for (TipPregleda tp : l.getSpecijalnosti()) {
			this.specijalnosti.add(new TipPregledaDTO(tp));
		}
		this.prosecnaOcena = l.getProsecnaOcena();
		this.slobodan = true;
	}

	public List<TipPregledaDTO> getSpecijalnosti() {
		return specijalnosti;
	}

	public void setSpecijalnosti(List<TipPregledaDTO> specijalnosti) {
		this.specijalnosti = specijalnosti;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public boolean isSlobodan() {
		return slobodan;
	}

	public void setSlobodan(boolean slobodan) {
		this.slobodan = slobodan;
	}

}
