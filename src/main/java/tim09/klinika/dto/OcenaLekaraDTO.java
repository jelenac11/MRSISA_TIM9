package tim09.klinika.dto;

import tim09.klinika.model.OcenaLekara;

public class OcenaLekaraDTO extends OcenaDTO {

	private LekarDTO lekar;
	
	public OcenaLekaraDTO() {
		
	}

	public OcenaLekaraDTO(long id, int vrednost, PacijentDTO ocenjivac, LekarDTO lekar) {
		super(id, vrednost, ocenjivac);
		this.lekar = lekar;
	}
	
	public OcenaLekaraDTO(OcenaLekara o) {
		this.lekar = new LekarDTO(o.getLekar());
		this.setId(o.getId());
		this.setOcenjivac(new PacijentDTO(o.getOcenjivac()));
		this.setVrednost(o.getVrednost());
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}
}
