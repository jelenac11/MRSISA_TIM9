package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.VerifikacioniToken;

public interface VerifikacioniTokenRepository extends JpaRepository<VerifikacioniToken, Long> {

	VerifikacioniToken findByToken(String token);
}
