package tim09.klinika.dto;

import java.util.Date;

import tim09.klinika.model.Popust;

public class PopustDTO {

	private Long id;
	private Date pocetak;
	private Date kraj;
	private double procenat;

	public PopustDTO() {

	}

	public PopustDTO(Popust p) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	public double getProcenat() {
		return procenat;
	}

	public void setProcenat(double procenat) {
		this.procenat = procenat;
	}
}
