package klinika.dto;

public class OcenaDTO {

	private long id;
	private int vrednost;
	private PacijentDTO ocenjivac;

	public OcenaDTO() {

	}

	public OcenaDTO(long id, int vrednost, PacijentDTO ocenjivac) {
		this.id = id;
		this.vrednost = vrednost;
		this.ocenjivac = ocenjivac;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public PacijentDTO getOcenjivac() {
		return ocenjivac;
	}

	public void setOcenjivac(PacijentDTO ocenjivac) {
		this.ocenjivac = ocenjivac;
	}
}
