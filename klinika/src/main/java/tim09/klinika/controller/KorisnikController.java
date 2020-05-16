package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.KorisnikDTO;
import tim09.klinika.dto.KorisnikTokenDTO;
import tim09.klinika.model.Autoritet;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.StavkaSifrarnika;
import tim09.klinika.security.TokenUtils;
import tim09.klinika.service.KorisnikService;

@RestController
@RequestMapping(value = "korisnici")
public class KorisnikController {

	@Autowired
	KorisnikService korisnikService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtils tokenUtils;

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<KorisnikDTO> promeniKorisnika(@RequestBody KorisnikDTO korisnikDTO) {

		Korisnik korisnik = korisnikService.findOne(korisnikDTO.getId());
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		korisnik.setAdresa(korisnikDTO.getAdresa());
		korisnik.setDrzava(korisnikDTO.getDrzava());
		korisnik.setGrad(korisnikDTO.getGrad());
		korisnik.setIme(korisnikDTO.getIme());
		korisnik.setPrezime(korisnikDTO.getPrezime());
		korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
		korisnik.setAktiviran(korisnikDTO.isAktiviran());
		korisnik.setPromenjenaLozinka(korisnikDTO.isPromenjenaLozinka());
		korisnik.setVerifikovan(korisnikDTO.isVerifikovan());

		korisnik = korisnikService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}

	@PutMapping(value = "dobaviUlogu", consumes = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<String> dobaviUlogu(@RequestBody KorisnikDTO korisnikDTO) {

		Korisnik korisnik = korisnikService.findOne(korisnikDTO.getId());
		Autoritet a = korisnik.getAutoriteti().get(0);

		return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
	}

	@PutMapping(value = "promeniLozinku", consumes = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<Boolean> promeniLozinku(@RequestBody PromenaLozinke novaLozinka) {

		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik ulogovan = korisnikService.findByEmail(trenutniKorisnik.getName());

		ulogovan.setLozinka(korisnikService.encodePassword(novaLozinka.lozinka));
		korisnikService.save(ulogovan);

		return ResponseEntity.ok(true);
	}

	@GetMapping(value = "/proveriEmail/{email}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<Boolean> proveriEmail(@PathVariable("email") String email) {
		Korisnik korisnik = korisnikService.findByEmail(email);
		if (korisnik != null) {
			return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(false, HttpStatus.CREATED);
	}

	static class PromenaLozinke {
		public String lozinka;
	}
}
