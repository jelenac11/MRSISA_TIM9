package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.model.Pacijent;
import tim09.klinika.service.KorisnikServis;

@RestController
public class KorisnikKontroler {

	@Autowired
	private KorisnikServis servis;
	
	
	// metoda koja vraca podatke za pacijenta
	@GetMapping("/api/getUserInfo")
	public ResponseEntity<Pacijent> vratiPodatke() {
		Pacijent p = servis.vratiKorisnika();
		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	}
}
