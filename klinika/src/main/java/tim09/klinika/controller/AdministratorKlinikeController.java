package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Sala;
import tim09.klinika.model.ZahtjevZaOdsustvom;
import tim09.klinika.service.AdministratorKlinikeServisImpl;


@RestController
public class AdministratorKlinikeController {
	
	@Autowired
	private AdministratorKlinikeServisImpl servis;

	
	@GetMapping("/ucitajLekare")
	public ResponseEntity<Collection<Lekar>> ucitajLekare() {
		Collection<Lekar> lekari = servis.vratiSveLekare();
		return new ResponseEntity<Collection<Lekar>>(lekari, HttpStatus.OK);
	}
	
	@GetMapping("/ucitajSale")
	public ResponseEntity<Collection<Sala>> ucitajSale() {
		Collection<Sala> sale = servis.vratiSveSale();
		return new ResponseEntity<Collection<Sala>>(sale, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajSalu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> dodajSalu(@RequestBody Sala sala) throws Exception {
		boolean uspesno = servis.dodajSalu(sala);
		return new ResponseEntity<Boolean>(uspesno, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/dodajLekara", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> dodajLekara(@RequestBody Lekar lekar) throws Exception {
		boolean uspesno = servis.dodajLekara(lekar);
		return new ResponseEntity<Boolean>(uspesno, HttpStatus.CREATED);
	}
	
	@GetMapping("/getZahteviNaCekanju")
	public ResponseEntity<ArrayList<ZahtjevZaOdsustvom>> vratiPodatke() {
		ArrayList<ZahtjevZaOdsustvom> zahtjevi = servis.vratiZahtjeveNaCekanju();
		return new ResponseEntity<ArrayList<ZahtjevZaOdsustvom>>(zahtjevi, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updateZahtjev", consumes = "application/json")
	public ResponseEntity<Boolean> updateZahtjev(@RequestBody ZahtjevZaOdsustvom zahtjev) {
		boolean uspjesno = servis.updateZahtjeveNaCekanju(zahtjev);	
		return new ResponseEntity<Boolean>(uspjesno, HttpStatus.OK);
		
	}
}
