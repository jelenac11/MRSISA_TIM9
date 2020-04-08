package tim09.klinika.repository;

import java.util.Collection;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Sala;

public interface LekarRepository {
	
	boolean dodajLekara(Lekar lekar);

	Collection<Lekar> vratiSveLekare();
	
}
