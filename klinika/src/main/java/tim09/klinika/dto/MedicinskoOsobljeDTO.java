package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.MedicinskoOsoblje;
import tim09.klinika.model.Odsustvo;

public class MedicinskoOsobljeDTO extends KorisnikDTO {

	private String klinika;
	private ArrayList<OdsustvoDTO> odsustva;
	private long pocetakRadnogVremena;
	private long krajRadnogVremena;

	public MedicinskoOsobljeDTO() {

	}

	public MedicinskoOsobljeDTO(MedicinskoOsoblje osoblje) {
		super(osoblje);
		if(osoblje==null) {
			return;
		}
		this.klinika = osoblje.getKlinika().getNaziv();
		this.odsustva = new ArrayList<OdsustvoDTO>();
		this.pocetakRadnogVremena = osoblje.getPocetakRadnogVremena();
		this.krajRadnogVremena = osoblje.getKrajRadnogVremena();
	}

	public MedicinskoOsobljeDTO(Klinika klinika, Set<Odsustvo> odsustva, long pocetakRadnogVremena,
			long krajRadnogVremena) {
		super();
		this.klinika = klinika.getNaziv();
		this.odsustva = new ArrayList<OdsustvoDTO>();
		for (Odsustvo ods : odsustva) {
			this.odsustva.add(new OdsustvoDTO(ods));
		}
		this.pocetakRadnogVremena = pocetakRadnogVremena;
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public MedicinskoOsobljeDTO(long id, String ime, String prezime) {
		this.setIme(ime);
		this.setPrezime(prezime);
		this.setId(id);
	}

	public String getKlinika() {
		return klinika;
	}

	public void setKlinika(String klinika) {
		this.klinika = klinika;
	}

	public long getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(long pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public long getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(long krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public ArrayList<OdsustvoDTO> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(ArrayList<OdsustvoDTO> odsustva) {
		this.odsustva = odsustva;
	}

}
