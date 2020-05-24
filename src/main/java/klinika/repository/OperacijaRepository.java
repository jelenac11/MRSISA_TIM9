package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import klinika.model.Operacija;

public interface OperacijaRepository extends JpaRepository<Operacija, Long> {

	@Query(value = "SELECT * FROM operacija op WHERE op.otkazana=false and op.vreme >= ?2 and op.vreme <= ?3 and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)=1", nativeQuery = true)
	List<Operacija> findByLekarIdAndVreme(Long id, long datum, long l);

	List<Operacija> findBySalaIdAndVremeAfterAndOtkazana(Long id, long time, boolean otk);

	@Query(value = "SELECT * FROM operacija op WHERE op.otkazana=false and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)=1", nativeQuery = true)
	List<Operacija> findByLekar(Long id);

	public List<Operacija> findByKlinikaIdAndSalaIdIsNullAndVremeAfterAndOtkazana(Long id, long time, boolean otk);

	@Query(value = "SELECT * FROM operacija op WHERE op.otkazana=false and (op.vreme >= ?2 or (op.vreme not between ?2 and (?2 + 3600000))) and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)>=1", nativeQuery = true)
	List<Operacija> findByLekarIdAndVremeAfterOrBetween(Long id, long time);

	List<Operacija> findByOtkazanaAndKlinikaIdAndVremeAfterAndSalaIdIsNotNull(boolean b, Long id, long time);

	List<Operacija> findByPacijentIdAndOtkazanaFalse(long id);

	@Query(value = "SELECT * FROM operacija op WHERE op.otkazana=false and op.vreme = ?1 and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?2)=1", nativeQuery = true)
	Operacija findByVremeAndLekarId(long vreme, long id);

	@Query(value = "SELECT * FROM operacija o where o.otkazana=false and o.sala_id is null", nativeQuery = true)
	List<Operacija> dobaviSveOperacijeBezSale();

	List<Operacija> findByPacijentIdAndOtkazanaFalseAndSalaIsNotNull(long id);
}
