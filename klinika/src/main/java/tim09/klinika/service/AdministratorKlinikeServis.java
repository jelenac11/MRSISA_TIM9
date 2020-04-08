package tim09.klinika.service;

import java.util.Collection;

import tim09.klinika.model.Sala;
import tim09.klinika.model.ZahtjevZaOdsustvom;

public interface AdministratorKlinikeServis {
	
	public Collection<Sala> vratiSveSale();
	
	public boolean dodajSalu(Sala sala);
}
