package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Autoritet;

public interface AutoritetRepository extends JpaRepository<Autoritet, Long> {
	Autoritet findByIme(String ime);
}
