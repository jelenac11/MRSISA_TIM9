package klinika.dto;

public class LekarPacijentDTO {

	private long idLekara;
	private long idPacijenta;

	public LekarPacijentDTO(long idLekara, long idPacijenta) {
		super();
		this.idLekara = idLekara;
		this.idPacijenta = idPacijenta;
	}

	public long getIdLekara() {
		return idLekara;
	}

	public void setIdLekara(long idLekara) {
		this.idLekara = idLekara;
	}

	public long getIdPacijenta() {
		return idPacijenta;
	}

	public void setIdPacijenta(long idPacijenta) {
		this.idPacijenta = idPacijenta;
	}

}
