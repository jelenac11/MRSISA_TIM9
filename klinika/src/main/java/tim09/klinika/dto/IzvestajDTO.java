package tim09.klinika.dto;

import java.util.ArrayList;

public class IzvestajDTO {

	private Long id;
	private String opis;
	private PregledDTO pregled;
	private StavkaSifrarnikaDTO dijagnoza;
	private ArrayList<ReceptDTO> recepti;

	public IzvestajDTO() {

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

	public PregledDTO getPregled() {
		return pregled;
	}

	public void setPregled(PregledDTO pregled) {
		this.pregled = pregled;
	}

	public StavkaSifrarnikaDTO getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(StavkaSifrarnikaDTO dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public ArrayList<ReceptDTO> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<ReceptDTO> recepti) {
		this.recepti = recepti;
	}
}
