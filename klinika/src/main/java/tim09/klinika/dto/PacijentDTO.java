package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Pacijent;

public class PacijentDTO extends KorisnikDTO {

	private String jbo;
	private String obrazlozenje;

	public PacijentDTO() {

	}
	
	public PacijentDTO(Pacijent p) {
		super(p);
		if(p==null) {
			return;
		}
		this.jbo = p.getJbo();
	}

	public String getJbo() {
		return jbo;
	}

	public void setJbo(String jbo) {
		this.jbo = jbo;
	}
	
	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

}
