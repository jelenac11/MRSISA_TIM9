package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

	public List<Pregled> findByTipPregledaIdAndOtkazanAndVremeGreaterThan(long id,boolean otkazano,long vreme);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.lekar_id = ?1 ORDER BY p.vreme", nativeQuery = true)
	public List<Pregled> findByLekarAndVreme(Long id, long datum, long l);
}
