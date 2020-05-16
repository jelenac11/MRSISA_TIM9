package tim09.klinika.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import tim09.klinika.dto.KorisnikTokenDTO;
import tim09.klinika.dto.OdgovorPregledDTO;
import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.dto.PorukaDTO;
import tim09.klinika.dto.PregledDTO;
import tim09.klinika.model.Autoritet;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Pacijent;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TokenPotvrdePregleda;
import tim09.klinika.model.VerifikacioniToken;
import tim09.klinika.model.ZdravstveniKarton;
import tim09.klinika.repository.AutoritetRepository;
import tim09.klinika.security.TokenUtils;
import tim09.klinika.security.auth.JwtAuthenticationRequest;

@Service
public class AutoritetService {

	@Autowired
	private AutoritetRepository autoritetRepository;

	@Autowired
	private KorisnikService userDetailsService;

	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private PregledService pregledService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private EmailService emailService;

	@Autowired
	private VerifikacioniTokenService verifikacioniTokenService;

	@Autowired
	private TokenPotvrdePregledaService tokenPotvrdePregledaService;

	public Object createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new PorukaDTO("Pogrešan email ili lozinka.");
		}

		Korisnik korisnik = (Korisnik) authentication.getPrincipal();

		if (!korisnik.isVerifikovan()) {
			return new PorukaDTO("Nalog nije verifikovan.");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenUtils.generateToken(korisnik.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		return new KorisnikTokenDTO(jwt, expiresIn);
	}

	public Object registrujNovog(PacijentDTO korisnik) {
		Pacijent pacijent = new Pacijent();

		pacijent.setEmail(korisnik.getEmail());
		pacijent.setLozinka(this.userDetailsService.encodePassword(korisnik.getLozinka()));
		pacijent.setIme(korisnik.getIme());
		pacijent.setPrezime(korisnik.getPrezime());
		pacijent.setAdresa(korisnik.getAdresa());
		pacijent.setGrad(korisnik.getGrad());
		pacijent.setDrzava(korisnik.getDrzava());
		pacijent.setBrojTelefona(korisnik.getBrojTelefona());
		pacijent.setJbo(korisnik.getJbo());

		List<Autoritet> autoriteti = new ArrayList<Autoritet>();
		Autoritet a = autoritetRepository.findByIme("ROLE_PACIJENT");
		autoriteti.add(a);

		pacijent.setAutoriteti(autoriteti);
		pacijent.setAktiviran(false);
		pacijent.setPromenjenaLozinka(true);
		pacijent.setVerifikovan(false);
		pacijent.setPoslednjaPromenaLozinke(System.currentTimeMillis());

		this.userDetailsService.save(pacijent);
		return new ResponseEntity<>(true, HttpStatus.CREATED);
	}

	public void updateRegZahtev(PacijentDTO pacijentDTO) throws MailException, InterruptedException {
		Pacijent p = (Pacijent) userDetailsService.findByEmail(pacijentDTO.getEmail());

		if (pacijentDTO.isAktiviran()) {
			p.setAktiviran(true);
			p.setKarton(new ZdravstveniKarton(p, 0, 0, 0, "N/A"));
			pacijentService.save(p);
			emailService.posaljiAktivacioniLink(p);
		} else {
			emailService.posaljiEmail(pacijentDTO.getEmail(), "Klinika registracija",
					"Poštovani, Vaš zahtev za registraciju je odbijen. Razlog je sledeći: "
							+ pacijentDTO.getObrazlozenje());
			pacijentService.remove(p.getId());
		}
	}

	public RedirectView potvrdaRegistracije(String token) {
		VerifikacioniToken vt = verifikacioniTokenService.findByToken(token);
		
		if (vt != null) {
			vt.getPacijent().setVerifikovan(true);
			pacijentService.save(vt.getPacijent());
			return new RedirectView("http://localhost:8081/#/potvrdaRegistracije");
		}
		return null;
	}
	
	public RedirectView potvrdaTerminaPregleda(String token) {
		TokenPotvrdePregleda tpp = tokenPotvrdePregledaService.findByToken(token);
		
		if (tpp != null) {
			return new RedirectView("http://localhost:8081/#/potvrdaTerminaPregleda/"+token);
		}
		return null;
	}


	public List<Autoritet> findById(Long id) {
		Autoritet aut = autoritetRepository.getOne(id);
		List<Autoritet> autoriteti = new ArrayList<>();
		autoriteti.add(aut);
		return autoriteti;
	}

	public List<Autoritet> findByName(String name) {
		Autoritet aut = autoritetRepository.findByIme(name);
		List<Autoritet> autoriteti = new ArrayList<>();
		autoriteti.add(aut);
		return autoriteti;
	}
	
	public RedirectView odgovorNaPotvrduTerminaPregleda(OdgovorPregledDTO odgovorPregledDTO) {
		TokenPotvrdePregleda tpp = tokenPotvrdePregledaService.findByToken(odgovorPregledDTO.getToken());
		
		if (tpp != null) {
			Pregled pregled=tpp.getPregled();
			emailService.obavijestiLekara(pregled, pregled.getLekar().getEmail());
			pregled.setZauzet(odgovorPregledDTO.isOdgovor());
			pregled.setPotvrdjen(true);
			if (!odgovorPregledDTO.isOdgovor()) {
				pregled.setPacijent(null);
			}
			pregledService.save(pregled);
			tokenPotvrdePregledaService.deleteById(tpp.getId());
			return new RedirectView("http://localhost:8081/#/");
		}
		return null;
	}

	public ResponseEntity<PregledDTO> dobaviPodatkeoPregledu(String token) {
		TokenPotvrdePregleda tpp=tokenPotvrdePregledaService.findByToken(token);
		Pregled pregled=tpp.getPregled();
		return new ResponseEntity<PregledDTO>(new PregledDTO(pregled),HttpStatus.OK);
	}

}
