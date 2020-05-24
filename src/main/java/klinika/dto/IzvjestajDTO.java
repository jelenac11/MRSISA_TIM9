package klinika.dto;

public class IzvjestajDTO {

	private long id;
	private long start;
	private long end;

	public IzvjestajDTO(long id, long start, long end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public IzvjestajDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
}
