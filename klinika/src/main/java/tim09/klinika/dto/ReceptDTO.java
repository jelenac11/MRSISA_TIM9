package tim09.klinika.dto;

import tim09.klinika.model.Recept;

public class ReceptDTO {

	private Long id;
	private String opis;
	private StavkaSifrarnikaDTO lek;

	public ReceptDTO() {

	}
	
	public ReceptDTO(Recept r) {
		this.id = r.getId();
		this.opis = r.getOpis();
		this.lek = new StavkaSifrarnikaDTO(r.getLek());
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

	public StavkaSifrarnikaDTO getLek() {
		return lek;
	}

	public void setLek(StavkaSifrarnikaDTO lek) {
		this.lek = lek;
	}
}
