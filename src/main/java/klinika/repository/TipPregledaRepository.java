package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import klinika.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {

	public TipPregleda findByNazivAndAktivan(String naziv, boolean aktivan);

	public List<TipPregleda> findByKlinikaIdAndAktivan(long id, boolean aktivan);

	@Query(value = "SELECT * FROM tip_pregleda WHERE klinika_id = ?1  and naziv = ?2 and aktivan=true", nativeQuery = true)
	public TipPregleda findByIdAndNazivAndAktivan(long id, String tipPregleda);

	public List<TipPregleda> findAllByAktivan(boolean aktivan);

}
