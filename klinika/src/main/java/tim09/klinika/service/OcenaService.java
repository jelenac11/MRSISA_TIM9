package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Ocena;
import tim09.klinika.model.OcenaKlinike;
import tim09.klinika.model.OcenaLekara;
import tim09.klinika.repository.KorisnikRepository;
import tim09.klinika.repository.OcenaKlinikeRepository;
import tim09.klinika.repository.OcenaLekaraRepository;
import tim09.klinika.repository.OcenaRepository;

@Service
public class OcenaService {

	@Autowired
	private OcenaRepository ocenaRepository;

	@Autowired
	private OcenaKlinikeRepository ocenaKlinikeRepository;
	
	@Autowired
	private OcenaLekaraRepository ocenaLekaraRepository;

	@Autowired
	private KorisnikService userDetailsService;

	public Ocena findOne(Long id) {
		return ocenaRepository.findById(id).orElseGet(null);
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}

	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public void remove(Long id) {
		ocenaRepository.deleteById(id);
	}

	public OcenaKlinike ucitajOcenuPacijentaKlinike(long klinikaId) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();

		Korisnik pacijent = userDetailsService.findByEmail(trenutniKorisnik.getName());

		return ocenaKlinikeRepository.findByOcenjivacIdAndKlinikaId(pacijent.getId(), klinikaId);
	}
	
	public OcenaLekara ucitajOcenuPacijentaLekara(long lekarId) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();

		Korisnik pacijent = userDetailsService.findByEmail(trenutniKorisnik.getName());
		
		return ocenaLekaraRepository.findByOcenjivacIdAndLekarId(pacijent.getId(), lekarId);
	}

}
