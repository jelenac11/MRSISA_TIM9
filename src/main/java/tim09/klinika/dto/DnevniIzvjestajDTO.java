package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class DnevniIzvjestajDTO {

	private ArrayList<Long> ukupno;
	private HashMap<String,Integer> krofna;
	
	public DnevniIzvjestajDTO(ArrayList<Long> ukupno, HashMap<String, Integer> krofna) {
		super();
		this.ukupno = ukupno;
		this.krofna = krofna;
	}
	public DnevniIzvjestajDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Long> getUkupno() {
		return ukupno;
	}
	public void setUkupno(ArrayList<Long> ukupno) {
		this.ukupno = ukupno;
	}
	public HashMap<String, Integer> getKrofna() {
		return krofna;
	}
	public void setKrofna(HashMap<String, Integer> krofna) {
		this.krofna = krofna;
	}
}
