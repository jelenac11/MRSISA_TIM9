package tim09.klinika.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Korisnik;

public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Long> {

	AdminKlinike findByEmail( String email );

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'AK' and k.klinika_id = ?1", nativeQuery = true)
	ArrayList<AdminKlinike> findAdminByKlinikaId(Long id);
}
