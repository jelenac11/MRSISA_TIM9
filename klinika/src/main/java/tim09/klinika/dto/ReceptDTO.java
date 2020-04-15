package tim09.klinika.dto;

public class ReceptDTO {

	private Long id;
	private String opis;
	private MedSestraDTO medSestra;
	private StavkaSifrarnikaDTO lek;

	public ReceptDTO() {

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

	public MedSestraDTO getMedSestra() {
		return medSestra;
	}

	public void setMedSestra(MedSestraDTO medSestra) {
		this.medSestra = medSestra;
	}

	public StavkaSifrarnikaDTO getLek() {
		return lek;
	}

	public void setLek(StavkaSifrarnikaDTO lek) {
		this.lek = lek;
	}
}
