package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Odsustvo;

public interface OdsustvoRepository extends JpaRepository<Odsustvo, Long> {

	List<Odsustvo> findByOdgovorenoFalseAndKlinikaId(long id);

	public List<Odsustvo> findByPodnosilacIdAndOdobreno(Long id, boolean odob);
	
	@Query(value = "SELECT * FROM odsustvo o WHERE o.podnosilac_id = ?1 and ((o.odgovoreno=true and o.odobreno=true) or (o.odgovoreno=false and o.odobreno=false)) and ((?2 between o.pocetak and o.kraj) or (?3 between o.pocetak and o.kraj) or (o.pocetak>=?2 and o.kraj<=?3))", nativeQuery = true)
	public List<Odsustvo> findByPodnosilacPocetakAndKraj(long id,long pocetak,long kraj);
	
	@Query(value = "SELECT * FROM odsustvo o WHERE o.podnosilac_id = ?1 and ((o.odgovoreno=true and o.odobreno=true) or (o.odgovoreno=false and o.odobreno=false)) and ((o.pocetak<=?2 and ?2<=o.kraj) or (o.pocetak between ?2 and (?2+86400000)))", nativeQuery = true)
	public List<Odsustvo> findByPodnosilacAndVreme(long id,long vreme);
}
