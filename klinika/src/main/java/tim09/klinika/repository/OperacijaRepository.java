package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;

public interface OperacijaRepository extends JpaRepository<Operacija, Long> {

	@Query(value = "SELECT * FROM operacija op WHERE op.vreme >= ?2 and op.vreme <= ?3 and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)=1", nativeQuery = true)
	List<Operacija> findByLekarIdAndVreme(Long id, long datum, long l);

	List<Operacija> findBySalaIdAndVremeAfter(Long id, long time);

	@Query(value = "SELECT * FROM operacija op WHERE (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)=1", nativeQuery = true)
	List<Operacija> findByLekar(Long id);

	public List<Operacija> findByKlinikaIdAndSalaIdIsNullAndVremeAfter(Long id, long time);

	@Query(value = "SELECT * FROM operacija op WHERE  (op.vreme >= ?2 or (op.vreme not between ?2 and (?2 + 3600000))) and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)>=1", nativeQuery = true)
	List<Operacija> findByLekarIdAndVremeAfterOrBetween(Long id, long time);
}
