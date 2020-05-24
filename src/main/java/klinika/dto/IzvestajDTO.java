package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Izvestaj;
import klinika.model.Recept;

public class IzvestajDTO {

	private Long id;
	private String opis;
	private StavkaSifrarnikaDTO dijagnoza;
	private List<ReceptDTO> recepti;
	private PregledDTO pregled;

	public IzvestajDTO() {
		super();
	}

	public IzvestajDTO(Izvestaj i) {
		this.id = i.getId();
		this.opis = i.getOpis();
		this.dijagnoza = new StavkaSifrarnikaDTO(i.getDijagnoza());
		this.pregled = new PregledDTO(i.getPregled());
		this.recepti = new ArrayList<>();
		for (Recept r : i.getRecepti()) {
			this.recepti.add(new ReceptDTO(r));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public StavkaSifrarnikaDTO getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(StavkaSifrarnikaDTO dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public List<ReceptDTO> getRecepti() {
		return recepti;
	}

	public void setRecepti(List<ReceptDTO> recepti) {
		this.recepti = recepti;
	}

	public PregledDTO getPregled() {
		return pregled;
	}

	public void setPregled(PregledDTO pregled) {
		this.pregled = pregled;
	}
}
