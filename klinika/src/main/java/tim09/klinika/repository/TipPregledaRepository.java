package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {

	public TipPregleda findByNaziv(String naziv);
	
	public List<TipPregleda> findByKlinikaId(long id);
	
}
