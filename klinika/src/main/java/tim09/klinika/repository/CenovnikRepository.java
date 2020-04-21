package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Cenovnik;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Long> {

}
