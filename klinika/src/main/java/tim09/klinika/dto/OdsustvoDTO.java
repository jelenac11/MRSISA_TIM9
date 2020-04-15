package tim09.klinika.dto;

import java.util.Date;

public class OdsustvoDTO {

	private Long id;
	private MedicinskoOsobljeDTO podnosilac;
	private Date pocetak;
	private Date kraj;
	private boolean odobreno;

	public OdsustvoDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicinskoOsobljeDTO getPodnosilac() {
		return podnosilac;
	}

	public void setPodnosilac(MedicinskoOsobljeDTO podnosilac) {
		this.podnosilac = podnosilac;
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

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}
}
