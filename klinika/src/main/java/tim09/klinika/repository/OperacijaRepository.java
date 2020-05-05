package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Operacija;

public interface OperacijaRepository extends JpaRepository<Operacija, Long> {

	@Query(value = "SELECT * FROM operacija op WHERE  op.vreme >= ?2 and op.vreme <= ?3 and (select count(o) from operisali o where operacija_id = op.operacija_id and lekar_id = ?1)=1 ORDER BY op.vreme", nativeQuery = true)
	List<Operacija> findByLekarIdAndVreme(Long id, long datum, long l);
	
}
