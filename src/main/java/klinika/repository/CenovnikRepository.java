package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Cenovnik;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Long> {

}
