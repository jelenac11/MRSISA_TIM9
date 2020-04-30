package tim09.klinika.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Korisnik;
import tim09.klinika.repository.KorisnikRepository;

@Service
public class KorisnikService implements UserDetailsService {
	
	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByEmail(email);
		if (korisnik == null) {
			throw new UsernameNotFoundException(String.format("Nije pronadjen korisnik sa email-om: '%s'.", email));
		} else {
			return korisnik;
		}
	}
	
	public Korisnik findByEmail(String email) {
		return korisnikRepository.findByEmail(email);
	}
	
	public Korisnik findOne(Long id) {
		return korisnikRepository.findById(id).orElseGet(null);
	}

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

	public void remove(Long id) {
		korisnikRepository.deleteById(id);
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");
			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		Korisnik korisnik = (Korisnik) loadUserByUsername(username);

		korisnik.setLozinka(passwordEncoder.encode(newPassword));
		korisnikRepository.save(korisnik);

	}
	
	public String encodePassword(String lozinka) {
		return passwordEncoder.encode(lozinka);
	}
}
