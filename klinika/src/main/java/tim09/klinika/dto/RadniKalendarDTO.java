package tim09.klinika.dto;

public class RadniKalendarDTO {

	private long start;
	private long end;
	private String naziv;

	public RadniKalendarDTO() {

	}

	public RadniKalendarDTO(long start, long end, String naziv) {
		this.start = start;
		this.end = end;
		this.naziv = naziv;
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

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
}
