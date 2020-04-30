package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.AdminKlinike;

public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Long> {

}
