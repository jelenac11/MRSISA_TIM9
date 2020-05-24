package tim09.klinika.dto;

public class MjesecniIzvjestajDTO {

	private long id;
	private String mjesec;
	public MjesecniIzvjestajDTO(long id, String mjesec) {
		super();
		this.id = id;
		this.mjesec = mjesec;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMjesec() {
		return mjesec;
	}
	public void setMjesec(String mjesec) {
		this.mjesec = mjesec;
	}
}
