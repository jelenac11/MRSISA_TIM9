package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Recept;

public interface ReceptRepository extends JpaRepository<Recept, Long> {

}
