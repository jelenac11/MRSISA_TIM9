package klinika.dto;

import java.util.Map;

public class IzvjestajPoslovanjaDTO {

	private long start;
	private long end;
	private double prihod;
	private Map<String, Double> krofna;

	public IzvjestajPoslovanjaDTO(long start, long end, double prihod, Map<String, Double> krofna) {
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

	public Map<String, Double> getKrofna() {
		return krofna;
	}

	public void setKrofna(Map<String, Double> krofna) {
		this.krofna = krofna;
	}
}
