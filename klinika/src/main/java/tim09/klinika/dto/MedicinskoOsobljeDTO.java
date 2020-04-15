package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.Date;

public class MedicinskoOsobljeDTO extends KorisnikDTO {

	private KlinikaDTO klinika;
	private ArrayList<OdsustvoDTO> odsustva;
	private Date pocetakRadnogVremena;
	private Date krajRadnogVremena;

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
	
	public Date getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(Date pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public Date getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(Date krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public MedicinskoOsobljeDTO() {

	}

	public ArrayList<OdsustvoDTO> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(ArrayList<OdsustvoDTO> odsustva) {
		this.odsustva = odsustva;
	}

}
