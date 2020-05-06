package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Sifrarnik;

public interface SifrarnikRepository extends JpaRepository<Sifrarnik, Long> {

	Sifrarnik findByTipSifrarnika(String tip);
}
