package tim09.klinika.service;

import java.util.Collection;
import java.util.List;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.model.Sala;

public interface AdministratorKlinikeServis {
	
	public Collection<Sala> vratiSveSale();
	
	public Collection<Lekar> vratiSveLekare();
	
	public boolean dodajSalu(Sala sala);
	
	public boolean dodajLekara(Lekar lekar);
	
	public List<Odsustvo> vratiZahtjeveNaCekanju();

	public Odsustvo updateZahtjeveNaCekanju(Odsustvo zahtjev);
}
