package tim09.klinika.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

	public List<Pregled> findByTipPregledaIdAndOtkazanAndVremeGreaterThan(long id,boolean otkazano,long vreme);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.lekar_id = ?1 ORDER BY p.vreme", nativeQuery = true)
	public List<Pregled> findByLekarAndVreme(Long id, long datum, long l);
	
	@Modifying
	@Transactional
	@Query(value = "insert into pregled(lekar_id,tip_pregleda_id,sala_id,vreme,trajanje,klinika_id) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
	public int insertPregled(long lekar,long tipPregleda,long sala,long vreme,int trajanje,long klinika); 

}
