package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.model.Pacijent;
import tim09.klinika.service.KorisnikServis;

@RestController
public class KorisnikKontroler {

	@Autowired
	private KorisnikServis servis;
	
	//metoda koja obradjuje post zahtev za izmenu podataka o korisniku
	@PostMapping(value = "/api/editUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> izmeniProfil(@RequestBody Pacijent p) throws Exception {
		servis.izmeniPodatke(p);
		return new ResponseEntity<>("Uspešno izmenjeni podaci!", HttpStatus.OK);
	}
	
	// metoda koja vraca podatke za pacijenta
	@GetMapping("/api/getUserInfo")
	public ResponseEntity<Pacijent> vratiPodatke() {
		Pacijent p = servis.vratiKorisnika();
		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	}
}
