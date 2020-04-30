package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Long> {

	Pacijent findByJbo( String jbo );
	
	@Query(value = "SELECT * FROM korisnici WHERE tip = 'PA' and aktiviran = false", nativeQuery = true)
	List<Pacijent> nadjiRegZahteve();
}
