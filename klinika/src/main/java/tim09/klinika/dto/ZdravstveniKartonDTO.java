package tim09.klinika.dto;

import java.util.ArrayList;

public class ZdravstveniKartonDTO {

	private Long id;
	private ArrayList<IzvestajDTO> bolesti;

	public ZdravstveniKartonDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<IzvestajDTO> getBolesti() {
		return bolesti;
	}

	public void setBolesti(ArrayList<IzvestajDTO> bolesti) {
		this.bolesti = bolesti;
	}
}
