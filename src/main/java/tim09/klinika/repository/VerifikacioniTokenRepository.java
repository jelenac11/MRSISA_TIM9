package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.VerifikacioniToken;

public interface VerifikacioniTokenRepository extends JpaRepository<VerifikacioniToken, Long> {

	VerifikacioniToken findByToken(String token);
}
