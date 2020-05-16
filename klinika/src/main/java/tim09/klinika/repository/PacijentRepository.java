package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long> {

	Pacijent findByJbo( String jbo );
	
	@Query(value = "SELECT * FROM korisnici WHERE tip = 'PA' and aktiviran = false", nativeQuery = true)
	List<Pacijent> nadjiRegZahteve();

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'PA' and "
			+ "((select count(*) from pregled p where p.klinika_id=?1 and p.pacijent_id=k.korisnik_id)>0 or "
			+ "(select count(*) from operacija o where o.otkazana=false and o.klinika_id=?1 and o.pacijent_id=k.korisnik_id)>0)", nativeQuery = true)
	List<Pacijent> findbyKlinikaId(Long id);
}
