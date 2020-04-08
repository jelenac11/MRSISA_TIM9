package tim09.klinika.repository;

import java.util.Collection;

import tim09.klinika.model.Lekar;

public interface LekarRepository {

	Collection<Lekar> vratiSveLekare();
	
}
