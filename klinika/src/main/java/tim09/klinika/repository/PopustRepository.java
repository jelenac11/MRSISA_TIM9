package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Popust;

public interface PopustRepository extends JpaRepository<Popust, Long> {

	@Query(value = "SELECT * FROM popust WHERE stavka_cenovnika_id = ?1 and pocetak <= ?2 and kraj >= ?3", nativeQuery = true)
	Popust nadjiPopust(Long id, Long vreme, Long vreme2);

	public List<Popust> findByKlinikaId(long id);
}
