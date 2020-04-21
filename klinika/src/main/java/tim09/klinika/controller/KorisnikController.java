package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.KorisnikDTO;
import tim09.klinika.model.Korisnik;
import tim09.klinika.service.KorisnikService;

@RestController
@RequestMapping(value = "korisnici")
public class KorisnikController {

	@Autowired
	KorisnikService korisnikService;

	@GetMapping(value = "/dobaviUlogovanog")
	public ResponseEntity<KorisnikDTO> ucitajSveSale() {
		List<Korisnik> svi = korisnikService.findAll();

		KorisnikDTO kor = null;
		for (Korisnik k : svi) {
			kor = new KorisnikDTO(k);
			break;
		}
		return new ResponseEntity<>(kor, HttpStatus.OK);
	}

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
		korisnik.setLozinka(korisnikDTO.getLozinka());
		korisnik.setPrezime(korisnikDTO.getPrezime());

		korisnik = korisnikService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}

}
