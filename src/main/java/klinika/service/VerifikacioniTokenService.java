package klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import klinika.model.VerifikacioniToken;
import klinika.repository.VerifikacioniTokenRepository;

@Service
public class VerifikacioniTokenService {

	@Autowired
	private VerifikacioniTokenRepository verifikacioniTokenRepository;

	public VerifikacioniToken findByToken(String token) {
		return verifikacioniTokenRepository.findByToken(token);
	}

	public void saveToken(VerifikacioniToken token) {
		verifikacioniTokenRepository.save(token);
	}
}
