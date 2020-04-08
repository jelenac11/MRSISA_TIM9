package tim09.klinika.repository;

import java.util.Collection;

import tim09.klinika.model.Sala;

public interface SalaRepository {

	boolean dodajSalu(Sala sala);

	Collection<Sala> vratiSveSale();

}