package klinika.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import klinika.model.Klinika;
import klinika.model.MedicinskoOsoblje;
import klinika.model.Odsustvo;

public class MedicinskoOsobljeDTO extends KorisnikDTO {

	private String klinika;
	private List<OdsustvoDTO> odsustva;
	private long pocetakRadnogVremena;
	private long krajRadnogVremena;

	public MedicinskoOsobljeDTO() {

	}

	public MedicinskoOsobljeDTO(MedicinskoOsoblje osoblje) {
		super(osoblje);
		if (osoblje == null) {
			return;
		}
		this.klinika = osoblje.getKlinika().getNaziv();
		this.odsustva = new ArrayList<>();
		this.pocetakRadnogVremena = osoblje.getPocetakRadnogVremena();
		this.krajRadnogVremena = osoblje.getKrajRadnogVremena();
	}

	public MedicinskoOsobljeDTO(Klinika klinika, Set<Odsustvo> odsustva, long pocetakRadnogVremena,
			long krajRadnogVremena) {
		super();
		this.klinika = klinika.getNaziv();
		this.odsustva = new ArrayList<>();
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

	public List<OdsustvoDTO> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(List<OdsustvoDTO> odsustva) {
		this.odsustva = odsustva;
	}

}
