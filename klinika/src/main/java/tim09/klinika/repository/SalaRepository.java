package tim09.klinika.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {

	Sala findByBroj(int broj);

	@Query(value = "select * from sala s where s.klinika_id=?1 and "
			+ "(select count(pr) from pregled pr where pr.sala_id=s.sala_id and (pr.vreme >= ?2 and pr.vreme <= (?2+?3)))=0 and "
			+ "(select count(op) from operacija op where op.sala_id=s.sala_id and (op.vreme >= ?2 and op.vreme <= (?2+?3)))=0;", nativeQuery = true)
	List<Sala> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme, long trajanje);

}