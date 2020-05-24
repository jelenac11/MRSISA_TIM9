package klinika.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import klinika.dto.KorisnikDTO;
import klinika.dto.KorisnikTokenDTO;
import klinika.dto.OdgovorPregledDTO;
import klinika.dto.PacijentDTO;
import klinika.dto.PregledDTO;
import klinika.model.Korisnik;
import klinika.model.Pacijent;
import klinika.security.TokenUtils;
import klinika.security.auth.JwtAuthenticationRequest;
import klinika.service.AutoritetService;
import klinika.service.KorisnikService;
import klinika.service.PacijentService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private KorisnikService userDetailsService;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private AutoritetService autoritetService;

	@GetMapping(value = "/dobaviUlogovanog")
	public ResponseEntity<KorisnikDTO> dobaviUlogovanog() {

		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();

		Korisnik ulogovan = userDetailsService.findByEmail(trenutniKorisnik.getName());
		KorisnikDTO korisnikDTO = new KorisnikDTO(ulogovan);
		return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
	}

	@PutMapping(value = "promeniLozinku", consumes = "application/json")
	public ResponseEntity<KorisnikDTO> promeniLozinku(@RequestBody KorisnikDTO korisnikDTO) {

		Korisnik korisnik = userDetailsService.findOne(korisnikDTO.getId());
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		korisnik.setLozinka(userDetailsService.encodePassword(korisnikDTO.getLozinka()));

		korisnik = userDetailsService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}

	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<KorisnikTokenDTO> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest) {

		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.ok(new KorisnikTokenDTO());
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Korisnik korisnik = (Korisnik) authentication.getPrincipal();

		String jwt = tokenUtils.generateToken(korisnik.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		return ResponseEntity.ok(new KorisnikTokenDTO(jwt, expiresIn));
	}

	@PutMapping(value = "/proveriEmail")
	public ResponseEntity<Boolean> proveriEmail(@RequestBody PacijentDTO p) {
		Korisnik k = userDetailsService.findByEmail(p.getEmail());
		if (k == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

	@PutMapping(value = "/proveriJbo")
	public ResponseEntity<Boolean> proveriJbo(@RequestBody PacijentDTO pac) {
		Pacijent p = pacijentService.findByJbo(pac.getJbo());
		if (p == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/dodajRegZahtev")
	public ResponseEntity<Boolean> dodajRegZahtev(@RequestBody PacijentDTO pacijentDTO) {
		return (ResponseEntity<Boolean>) autoritetService.registrujNovog(pacijentDTO);
	}

	@PutMapping(value = "/updateRegZahtev")
	public void updateRegZahtev(@RequestBody PacijentDTO pacijentDTO) throws MailException, InterruptedException {
		autoritetService.updateRegZahtev(pacijentDTO);
	}

	@GetMapping(value = "/potvrdaRegistracije/{token}")
	public RedirectView potvrdaReg(@PathVariable("token") String url) {
		return autoritetService.potvrdaRegistracije(url);
	}

	@GetMapping(value = "/potvrdaTerminaPregleda/{token}")
	public RedirectView potvrdaTerminaPregleda(@PathVariable("token") String url) {
		return autoritetService.potvrdaTerminaPregleda(url);
	}

	@GetMapping(value = "/dobaviPodatkeoPregledu/{token}")
	public ResponseEntity<PregledDTO> dobaviPodatkeoPregledu(@PathVariable("token") String token) {
		return autoritetService.dobaviPodatkeoPregledu(token);
	}

	@PostMapping(value = "/odgovorNaPotvrduTerminaPregleda")
	public RedirectView odgovorNaPotvrduTerminaPregleda(@RequestBody OdgovorPregledDTO odgovorPregledDTO) {
		return autoritetService.odgovorNaPotvrduTerminaPregleda(odgovorPregledDTO);
	}

	@PostMapping(value = "/proveriStanjePregleda")
	public ResponseEntity<String> proveriStanjePregleda(@RequestBody OdgovorPregledDTO odgovorPregledDTO) {
		return autoritetService.proveriStanjePregleda(odgovorPregledDTO);
	}

	@PostMapping(value = "/refresh")
	public ResponseEntity<KorisnikTokenDTO> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, new Date(korisnik.getPoslednjaPromenaLozinke()))) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new KorisnikTokenDTO(refreshedToken, expiresIn));
		} else {
			KorisnikTokenDTO userTokenState = new KorisnikTokenDTO();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

}
