package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import klinika.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {

	Sala findByBrojAndAktivan(int broj, boolean aktivan);

	Sala findByIdAndAktivan(long id, boolean aktivan);

	Sala findByNazivAndAktivan(String naziv, boolean aktivan);

	@Transactional
	@Query(value = "select * from sala s where s.klinika_id=?1 and s.aktivan=true and"
			+ "(select count(pr) from pregled pr where pr.sala_id=s.sala_id and pr.otkazan=false and (pr.vreme >= ?2 and pr.vreme < (?2+?3)))=0 and "
			+ "(select count(op) from operacija op where op.otkazana=false and op.sala_id=s.sala_id and (op.vreme >= ?2 and op.vreme < (?2+?3)))=0;", nativeQuery = true)
	List<Sala> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme, long trajanje);

	@Query(value = "select * from sala s where s.sala_id=?1 and s.aktivan=true and"
			+ "(select count(pr) from pregled pr where pr.sala_id=s.sala_id and pr.otkazan=false and (pr.vreme >= ?2 or pr.vreme+pr.trajanje >= ?2) and (pr.otkazan = false))=0 and "
			+ "(select count(op) from operacija op where op.otkazana=false and op.sala_id=s.sala_id and op.vreme >= ?2)=0;", nativeQuery = true)
	List<Sala> findBySalaIdAndVreme(long id, long vreme);

	List<Sala> findAllByAktivan(boolean b);

	List<Sala> findAllByKlinikaIdAndAktivan(Long id,boolean aktivan);
}