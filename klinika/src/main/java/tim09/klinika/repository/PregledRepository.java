package tim09.klinika.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

	@Query(value = "select * from pregled pr where pr.tip_pregleda_id=?1 and"
			+ "((pr.vreme >= ?2 or pr.vreme+pr.trajanje >= ?2) or (pr.pacijent_id is not null and pr.otkazan = false));", nativeQuery = true)
	public List<Pregled> findByTipPregledaIdAndVremeGreaterThan(long id, long vreme);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.lekar_id = ?1", nativeQuery = true)
	public List<Pregled> findByLekarAndVreme(Long id, long datum, long l);

	@Modifying
	@Transactional
	@Query(value = "insert into pregled(lekar_id,tip_pregleda_id,sala_id,vreme,trajanje,klinika_id,pacijent_id, otkazan, zauzet, potvrdjen) values(?1,?2,?3,?4,?5,?6,null,false,false,true)", nativeQuery = true)
	public int insertPregled(long lekar, long tipPregleda, long sala, long vreme, int trajanje, long klinika);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme >= ?2 and p.vreme <= ?3 and p.klinika_id = ?1 and p.tip_pregleda_id = ?4 and p.pacijent_id is null", nativeQuery = true)
	public List<Pregled> findByKlinikaAndVremeAndTip(long klinika_id, long vreme, long kraj, long tip);

	@Query(value = "SELECT * FROM pregled p WHERE p.vreme = ?1 and p.lekar_id = ?2", nativeQuery = true)
	public Pregled findPregledByVremeAndLekarId(long datum, Long id);

	@Modifying
	@Transactional
	@Query(value = "insert into pregled(lekar_id, pacijent_id, tip_pregleda_id, vreme, trajanje, klinika_id, sala_id, otkazan, zauzet, potvrdjen) values (?1, ?2, ?3, ?4, 3600000, ?5, null, false, true, false)", nativeQuery = true)
	public void insertZakazaniPregled(long lekar, long pacijent, long tip, long vreme, long klinika_id);

	public List<Pregled> findByOtkazanAndZauzetAndKlinikaIdAndVremeAfterAndSalaIdIsNotNullAndPotvrdjen(boolean b,
			boolean c, long id, long vreme, boolean potvrdjen);

	public List<Pregled> findByKlinikaIdAndSalaIdIsNullAndVremeAfterAndPotvrdjen(Long id, long time, boolean potvrdjen);

	public List<Pregled> findBySalaIdAndVremeAfter(Long id, long time);

	public List<Pregled> findByLekarId(Long id);

	@Query(value = "SELECT * FROM pregled p WHERE p.lekar_id = ?1 and (p.vreme >= ?2 or (p.vreme not between ?2 and (?2 + 3600000)))", nativeQuery = true)
	public List<Pregled> findByLekarIdAndVremeAfterOrBetween(Long id, long time);

	public List<Pregled> findByPacijentIdAndPotvrdjenAndOtkazan(long id, boolean b, boolean c);

	@Query(value = "SELECT * FROM pregled p where p.lekar_id=?1 and p.pacijent_id=?2 and p.otkazan=false and (p.vreme<=?3 or ?3 between p.vreme and (p.vreme+p.trajanje))", nativeQuery = true)
	public List<Pregled> findByLekarIdAndPacijentIdAndVreme(long idLekara, long idPacijenta, long time);

	public List<Pregled> findByKlinikaIdAndVremeAfterAndPacijentIsNull(long id, long vreme);
	
	@Query(value = "SELECT * FROM pregled p where p.lekar_id=?1 and p.pacijent_id=?2 and p.otkazan=false and ?3 between p.vreme and (p.vreme+3600000)", nativeQuery = true)
	public Pregled mozeZapocetiPregled(long idLekara, long idPacijenta, long time);
	
}
