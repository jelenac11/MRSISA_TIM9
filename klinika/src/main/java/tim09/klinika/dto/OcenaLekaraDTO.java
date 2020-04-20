package tim09.klinika.dto;

public class OcenaLekaraDTO extends OcenaDTO {

	private LekarDTO lekar;
	
	public OcenaLekaraDTO() {
		
	}

	public OcenaLekaraDTO(long id, int vrednost, PacijentDTO ocenjivac, LekarDTO lekar) {
		super(id, vrednost, ocenjivac);
		this.lekar = lekar;
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}
}
