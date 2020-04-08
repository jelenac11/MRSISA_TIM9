package tim09.klinika.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Sala;

@Repository
public class InMemoryLekarRepository implements LekarRepository {

	private final ConcurrentMap<String, Lekar> lekari = new ConcurrentHashMap<String, Lekar>();
	
	@Override
	public boolean dodajLekara(Lekar lekar) {
		this.lekari.put(lekar.getEmail(), lekar);
		return true;
	}
	
	@Override
	public Collection<Lekar> vratiSveLekare() {
		return this.lekari.values();
	}
	
}
