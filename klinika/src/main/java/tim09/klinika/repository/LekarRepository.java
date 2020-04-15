package tim09.klinika.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

}
