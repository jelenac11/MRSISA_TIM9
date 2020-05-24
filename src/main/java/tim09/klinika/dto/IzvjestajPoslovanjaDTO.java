package tim09.klinika.dto;

import java.util.HashMap;

public class IzvjestajPoslovanjaDTO {

	long start;
	long end;
	double prihod;
	HashMap<String,Double> krofna;
	
	public IzvjestajPoslovanjaDTO(long start, long end, double prihod,HashMap<String,Double> krofna) {
		super();
		this.start = start;
		this.end = end;
		this.prihod = prihod;
		this.krofna = krofna;
	
	}
	public IzvjestajPoslovanjaDTO() {
		super();
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public double getPrihod() {
		return prihod;
	}
	public void setPrihod(double prihod) {
		this.prihod = prihod;
	}
	public HashMap<String, Double> getKrofna() {
		return krofna;
	}
	public void setKrofna(HashMap<String, Double> krofna) {
		this.krofna = krofna;
	}
}
