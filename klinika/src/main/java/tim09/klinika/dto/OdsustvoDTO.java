package tim09.klinika.dto;

import java.util.Date;

import tim09.klinika.model.MedicinskoOsoblje;
import tim09.klinika.model.Odsustvo;

public class OdsustvoDTO {

	private Long id;
	private MedicinskoOsobljeDTO podnosilac;
	private Date pocetak;
	private Date kraj;
	private boolean odgovoreno;
	private boolean odobreno;
	private String obrazlozenje;
	private KlinikaDTO klinika;
	
	public OdsustvoDTO() {

	}

	
	
	public OdsustvoDTO(Long id, MedicinskoOsoblje podnosilac, Date pocetak, Date kraj, boolean odgovoreno,
			boolean odobreno,String obrazlozenje, KlinikaDTO klinika) {
		super();
		this.id = id;
		this.podnosilac = new MedicinskoOsobljeDTO(podnosilac);
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.odgovoreno = odgovoreno;
		this.odobreno = odobreno;
		this.obrazlozenje=obrazlozenje;
		this.klinika = klinika;
	}



	public KlinikaDTO getKlinika() {
		return klinika;
	}



	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}



	public OdsustvoDTO(Odsustvo odsustvo) {
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
	

	public boolean isOdgovoreno() {
		return odgovoreno;
	}

	public void setOdgovoreno(boolean odgovoreno) {
		this.odgovoreno = odgovoreno;
	}

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}



	public String getObrazlozenje() {
		return obrazlozenje;
	}



	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
	
}
