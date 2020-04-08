package tim09.klinika.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import tim09.klinika.model.Sala;

@Repository
public class InMemorySalaRepository implements SalaRepository {

	private final ConcurrentMap<Integer, Sala> sale = new ConcurrentHashMap<Integer, Sala>();

	@Override
	public boolean dodajSalu(Sala sala) {
		this.sale.put(sala.getBroj(), sala);
		return true;
	}

	@Override
	public Collection<Sala> vratiSveSale() {
		return this.sale.values();
	}

}
