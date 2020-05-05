package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.service.StavkaCenovnikaService;

@RestController
@RequestMapping(value = "stavkeCenovnika")
public class StavkaCenovnikaController {
	
	@Autowired
	private StavkaCenovnikaService stavkaCenovnikaService;
	
	@PutMapping(value = "/dobaviCenuPregleda", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Double> dobaviCenuPregleda(@RequestBody PretragaKlinikeDTO pretragaKlinikeDTO) {
		return new ResponseEntity<>(stavkaCenovnikaService.vratiCenuPregleda(pretragaKlinikeDTO), HttpStatus.OK);
	}
}
