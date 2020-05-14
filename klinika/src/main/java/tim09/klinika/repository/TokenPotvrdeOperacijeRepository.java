package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.TokenPotvrdeOperacije;
import tim09.klinika.model.TokenPotvrdePregleda;

public interface TokenPotvrdeOperacijeRepository extends JpaRepository<TokenPotvrdeOperacije, Long> {

	TokenPotvrdeOperacije findByToken(String token);
}
