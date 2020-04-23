package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.MedicinskoOsoblje;
import tim09.klinika.model.Odsustvo;

public class MedicinskoOsobljeDTO extends KorisnikDTO {

	private KlinikaDTO klinika;
	private ArrayList<OdsustvoDTO> odsustva;
	private Date pocetakRadnogVremena;
	private Date krajRadnogVremena;

	public MedicinskoOsobljeDTO() {

	}
	
	public MedicinskoOsobljeDTO(MedicinskoOsoblje osoblje) {
		super(osoblje);
	}

	public MedicinskoOsobljeDTO(Klinika klinika, Set<Odsustvo> odsustva, Date pocetakRadnogVremena,
			Date krajRadnogVremena) {
		super();
		this.klinika = new KlinikaDTO(klinika);
		this.odsustva = new ArrayList<OdsustvoDTO>();
		for (Odsustvo ods : odsustva) {
			this.odsustva.add(new OdsustvoDTO(ods));
		}
		this.pocetakRadnogVremena = pocetakRadnogVremena;
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public MedicinskoOsobljeDTO(long id,String ime,String prezime){
		this.setIme(ime);
		this.setPrezime(prezime);
		this.setId(id);
	}
	
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

	public ArrayList<OdsustvoDTO> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(ArrayList<OdsustvoDTO> odsustva) {
		this.odsustva = odsustva;
	}

}
