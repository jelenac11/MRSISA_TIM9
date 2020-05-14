package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Odsustvo;

public interface OdsustvoRepository extends JpaRepository<Odsustvo, Long> {

	List<Odsustvo> findByOdgovorenoFalseAndKlinikaId(long id);

	public List<Odsustvo> findByPodnosilacAndKrajAfter(Long id, long time);
}
