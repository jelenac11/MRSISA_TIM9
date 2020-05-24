package klinika.dto;

import java.util.List;
import java.util.Map;

public class DnevniIzvjestajDTO {

	private List<Long> ukupno;
	private Map<String, Integer> krofna;

	public DnevniIzvjestajDTO(List<Long> ukupno, Map<String, Integer> krofna) {
		this.ukupno = ukupno;
		this.krofna = krofna;
	}

	public DnevniIzvjestajDTO() {
		super();
	}

	public List<Long> getUkupno() {
		return ukupno;
	}

	public void setUkupno(List<Long> ukupno) {
		this.ukupno = ukupno;
	}

	public Map<String, Integer> getKrofna() {
		return krofna;
	}

	public void setKrofna(Map<String, Integer> krofna) {
		this.krofna = krofna;
	}
}
