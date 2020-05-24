package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.PacijentDTO;
import klinika.model.Pacijent;
import klinika.service.KorisnikService;
import klinika.service.PacijentService;

@RestController
@RequestMapping(value = "pacijenti")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private KorisnikService korisnikService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<PacijentDTO> ucitajPoId(@PathVariable Long id) {
		Pacijent p = pacijentService.findOne(id);
		PacijentDTO pac = new PacijentDTO(p);
		return new ResponseEntity<>(pac, HttpStatus.OK);
	}

	@GetMapping(value = "/vratiRegZahteve")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<List<PacijentDTO>> vratiRegZahteve() {
		List<Pacijent> pacijenti = pacijentService.nadjiRegZahteve();
		List<PacijentDTO> pacijentDTO = new ArrayList<>();
		for (Pacijent p : pacijenti) {
			pacijentDTO.add(new PacijentDTO(p));
		}
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<PacijentDTO> promeniKorisnika(@RequestBody PacijentDTO korisnikDTO) {

		Pacijent korisnik = pacijentService.findOne(korisnikDTO.getId());
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

		korisnik = pacijentService.save(korisnik);
		return new ResponseEntity<>(new PacijentDTO(korisnik), HttpStatus.OK);
	}

	@GetMapping("dobaviSvePoIdKlinike/{id}")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<PacijentDTO>> dobaviSvePoIdKlinike(@PathVariable Long id) {
		List<PacijentDTO> p = pacijentService.findByKlinikaID(id);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
}