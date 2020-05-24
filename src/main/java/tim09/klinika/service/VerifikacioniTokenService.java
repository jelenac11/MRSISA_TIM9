package tim09.klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim09.klinika.model.VerifikacioniToken;
import tim09.klinika.repository.VerifikacioniTokenRepository;

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
