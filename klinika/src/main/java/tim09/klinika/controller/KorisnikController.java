package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.KorisnikDTO;
import tim09.klinika.model.Autoritet;
import tim09.klinika.model.Korisnik;
import tim09.klinika.service.KorisnikService;

@RestController
@RequestMapping(value = "korisnici")
public class KorisnikController {

	@Autowired
	KorisnikService korisnikService;

	@PutMapping(consumes = "application/json")
	public ResponseEntity<KorisnikDTO> promeniKorisnika(@RequestBody KorisnikDTO korisnikDTO) {

		Korisnik korisnik = korisnikService.findOne(korisnikDTO.getId());
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		korisnik.setAdresa(korisnikDTO.getAdresa());
		korisnik.setDrzava(korisnikDTO.getDrzava());
		korisnik.setGrad(korisnikDTO.getGrad());
		korisnik.setIme(korisnikDTO.getIme());
		korisnik.setLozinka(korisnikService.encodePassword(korisnikDTO.getLozinka()));
		korisnik.setPrezime(korisnikDTO.getPrezime());
		korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
		korisnik.setAktiviran(korisnikDTO.isAktiviran());
		korisnik.setPromenjenaLozinka(korisnikDTO.isPromenjenaLozinka());
		korisnik.setVerifikovan(korisnikDTO.isVerifikovan());

		korisnik = korisnikService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}
	
	@PutMapping(value = "dobaviUlogu", consumes = "application/json")
	public ResponseEntity<String> dobaviUlogu(@RequestBody KorisnikDTO korisnikDTO) {

		Korisnik korisnik = korisnikService.findOne(korisnikDTO.getId());
		Autoritet a = korisnik.getAutoriteti().get(0);

		return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
	}

}
