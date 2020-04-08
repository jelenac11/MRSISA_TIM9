package tim09.klinika.service;

import java.util.Collection;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Sala;
import tim09.klinika.model.ZahtjevZaOdsustvom;

public interface AdministratorKlinikeServis {
	
	public Collection<Sala> vratiSveSale();
	
	public Collection<Lekar> vratiSveLekare();
	
	public boolean dodajSalu(Sala sala);
	
	public boolean dodajLekara(Lekar lekar);
}
