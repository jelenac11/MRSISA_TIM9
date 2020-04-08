package tim09.klinika.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.model.ZahtjevZaOdsustvom;
import tim09.klinika.service.AdministratorKlinikeServis;


@RestController
public class AdministratorKlinikeController {

	@Autowired
	private AdministratorKlinikeServis servis;
	
	@GetMapping("/api/getZahteviNaCekanju")
	public ResponseEntity<ArrayList<ZahtjevZaOdsustvom>> vratiPodatke() {
		ArrayList<ZahtjevZaOdsustvom> zahtjevi = servis.vratiZahtjeveNaCekanju();
		return new ResponseEntity<ArrayList<ZahtjevZaOdsustvom>>(zahtjevi, HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/updateZahtjev", consumes = "application/json")
	public void updateZahtjev(@RequestBody ZahtjevZaOdsustvom zahtjev) {
		servis.updateZahtjeveNaCekanju(zahtjev);
		
	}
}
