package tim09.klinika.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1 and (select count(l) from specijalizovan l where l.lekar_id=k.korisnik_id and l.tip_pregleda_id=?2)=1 and "
			+ "(select count(o) from odsustvo o where o.podnosilac_id=k.korisnik_id and (o.pocetak <= ?3 and  ?3 <= o.kraj))=0", nativeQuery = true)
	List<Lekar> findBySearchParams(Long id, Long pregledId, Long datum);
	
	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1", nativeQuery = true)
	List<Lekar> findByKlinikaId(Long id);
	
	List<Lekar> findAllByKlinika(Klinika k);
}
