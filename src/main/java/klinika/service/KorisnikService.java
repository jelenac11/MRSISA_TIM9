package klinika.service;

import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.OdsustvoDTO;
import klinika.model.Klinika;
import klinika.model.Korisnik;
import klinika.model.Lekar;
import klinika.model.MedicinskoOsoblje;
import klinika.model.Odsustvo;
import klinika.model.Operacija;
import klinika.model.Pregled;
import klinika.repository.KorisnikRepository;
import klinika.repository.MedicinskoOsobljeRepository;
import klinika.repository.OdsustvoRepository;
import klinika.repository.OperacijaRepository;
import klinika.repository.PregledRepository;

@Service
public class KorisnikService implements UserDetailsService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PregledRepository pregledRepository;

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private OdsustvoRepository odsustvoRepository;
	
	@Autowired
	private LekarService lekarService;

	@Autowired
	private MedicinskoOsobljeRepository medicinskoOsobljeRepository;

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

	// Metoda koja radi promenu lozinke
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			logger.debug("Re-authenticating user '" + username + "' for password change request.");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			logger.debug("No authentication manager set. can't change Password!");
			return;
		}

		logger.debug("Changing password for user '" + username + "'");

		Korisnik korisnik = (Korisnik) loadUserByUsername(username);

		korisnik.setLozinka(passwordEncoder.encode(newPassword));
		korisnikRepository.save(korisnik);

	}

	public String encodePassword(String lozinka) {
		return passwordEncoder.encode(lozinka);
	}

	// Metoda koja dodaje zahteve za odsustvo
	@Transactional(readOnly = false)
	public boolean zahtevOdsustvo(OdsustvoDTO odsustvoDTO) {
		List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(odsustvoDTO.getPodnosilac().getId(),
				odsustvoDTO.getPocetak(), odsustvoDTO.getKraj());
		List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(odsustvoDTO.getPodnosilac().getId(),
				odsustvoDTO.getPocetak(), odsustvoDTO.getKraj());
		List<Odsustvo> odsustva = odsustvoRepository.findByPodnosilacPocetakAndKraj(odsustvoDTO.getPodnosilac().getId(),
				odsustvoDTO.getPocetak(), odsustvoDTO.getKraj());
		MedicinskoOsoblje kor = medicinskoOsobljeRepository.getOne(odsustvoDTO.getPodnosilac().getId());
		Klinika k = kor.getKlinika();

		if (pregledi.isEmpty() && operacije.isEmpty() && odsustva.isEmpty()) {
			Odsustvo odsustvo = new Odsustvo();
			odsustvo.setKlinika(k);
			odsustvo.setKraj(odsustvoDTO.getKraj());
			odsustvo.setPocetak(odsustvoDTO.getPocetak());
			odsustvo.setOdgovoreno(false);
			odsustvo.setOdobreno(false);
			odsustvo.setObrazlozenje("");
			odsustvo.setPodnosilac(kor);
			Lekar l = lekarService.findOne(kor.getId());
			if (l != null) {
				l.setLastChange(new Date().getTime());
				lekarService.save(l);
			}
			odsustvo.setKlinika(k);
			odsustvoRepository.save(odsustvo);
			return true;
		}
		return false;
	}
}
