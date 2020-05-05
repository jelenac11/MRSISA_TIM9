package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Pregled;
import tim09.klinika.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {

	public TipPregleda findByNaziv(String naziv);
	
	public List<TipPregleda> findByKlinikaId(long id);

	@Query(value = "SELECT * FROM tip_pregleda WHERE klinika_id = ?1  and naziv = ?2", nativeQuery = true)
	public TipPregleda findByIdAndNaziv(long id, String tipPregleda);
	
}
