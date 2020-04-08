package tim09.klinika.model;


public class ZahtjevZaOdsustvom {

	private MedicinskoOsoblje osoba;
	private long pocetak_odsustva;
	private long kraj_odsustva;
	
	//status odustva na cekanju(false)/odgovoreno na zahtjev(true)
	private boolean status;
	
	//stanje zahtjeva prihvaceno(true)/odbijeno(false)
	private boolean stanje;
	
	private String obrazlozenje;
	
	public ZahtjevZaOdsustvom(MedicinskoOsoblje osoba, long pocetak_odsustva, long kraj_odsustva, boolean status,
			boolean stanje,String obrazlozenje) {
		super();
		this.osoba = osoba;
		this.pocetak_odsustva = pocetak_odsustva;
		this.kraj_odsustva = kraj_odsustva;
		this.status = status;
		this.stanje = stanje;
		this.obrazlozenje=obrazlozenje;
	}

	public ZahtjevZaOdsustvom() {
		super();
	}

	public MedicinskoOsoblje getOsoba() {
		return osoba;
	}

	public void setOsoba(MedicinskoOsoblje osoba) {
		this.osoba = osoba;
	}

	public long getPocetak_odsustva() {
		return pocetak_odsustva;
	}

	public void setPocetak_odsustva(long pocetak_odsustva) {
		this.pocetak_odsustva = pocetak_odsustva;
	}

	public long getKraj_odsustva() {
		return kraj_odsustva;
	}

	public void setKraj_odsustva(long kraj_odsustva) {
		this.kraj_odsustva = kraj_odsustva;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStanje() {
		return stanje;
	}

	public void setStanje(boolean stanje) {
		this.stanje = stanje;
	}
	

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		ZahtjevZaOdsustvom zahtjev= (ZahtjevZaOdsustvom) obj;
		if(zahtjev.pocetak_odsustva==this.pocetak_odsustva && zahtjev.kraj_odsustva==this.kraj_odsustva && zahtjev.osoba.equals(this.osoba)) {
			return true;
		}
		return false;
	}
	
}
