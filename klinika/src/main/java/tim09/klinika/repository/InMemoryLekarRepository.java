package tim09.klinika.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Sala;

@Repository
public class InMemoryLekarRepository implements LekarRepository {

	private final ConcurrentMap<Integer, Lekar> lekari = new ConcurrentHashMap<Integer, Lekar>();
	
	@Override
	public Collection<Lekar> vratiSveLekare() {
		return this.lekari.values();
	}
	
}
