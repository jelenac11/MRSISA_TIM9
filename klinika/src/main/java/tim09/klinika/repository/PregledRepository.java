package tim09.klinika.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

	public List<Pregled> findByTipPregledaIdAndOtkazanAndVremeGreaterThan(long id,boolean otkazano,long vreme);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.lekar_id = ?1", nativeQuery = true)
	public List<Pregled> findByLekarAndVreme(Long id, long datum, long l);
	
	@Modifying
	@Transactional
	@Query(value = "insert into pregled(lekar_id,tip_pregleda_id,sala_id,vreme,trajanje,klinika_id,pacijent_id) values(?1,?2,?3,?4,?5,?6,null)",nativeQuery = true)
	public int insertPregled(long lekar,long tipPregleda,long sala,long vreme,int trajanje,long klinika); 
	
	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.klinika_id = ?1 and p.tip_pregleda_id = ?4 and p.pacijent_id is null", nativeQuery = true)
	public List<Pregled> findByKlinikaAndVremeAndTip(long klinika_id, long vreme, long kraj, long tip);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme = ?1 and p.lekar_id = ?2", nativeQuery = true)
	public Pregled findPregledByVremeAndLekarId(long datum, Long id);
	
	@Modifying
	@Transactional
	@Query(value = "insert into pregled(lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, sala_id, otkazan) values (?1, ?2, ?3, ?4, 3600000, ?5, null, false)",nativeQuery = true)
	public void insertZakazaniPregled(long lekar, long pacijent, long tip, long vreme, long klinika_id);

}
