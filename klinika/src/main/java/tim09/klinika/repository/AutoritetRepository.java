package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Autoritet;

public interface AutoritetRepository extends JpaRepository<Autoritet, Long> {
	Autoritet findByIme(String ime);
}
