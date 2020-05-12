package tim09.klinika.dto;

public class OdgovorPregledDTO {

	private String token;
	private boolean odgovor;
	
	public OdgovorPregledDTO() {
		super();
	}
	
	public OdgovorPregledDTO(String token, boolean odgovor) {
		super();
		this.token = token;
		this.odgovor = odgovor;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isOdgovor() {
		return odgovor;
	}

	public void setOdgovor(boolean odgovor) {
		this.odgovor = odgovor;
	}
}
