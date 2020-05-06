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
import tim09.klinika.dto.StavkaCenovnikaDTO;
import tim09.klinika.model.StavkaCenovnika;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.service.StavkaCenovnikaService;
import tim09.klinika.service.TipPregledaService;

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
	
	@PutMapping(value = "/izmeni", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izmeni(@RequestBody StavkaCenovnikaDTO stavkaCenovnikaDTO) {
		StavkaCenovnika stavka = stavkaCenovnikaService.findOne(stavkaCenovnikaDTO.getId());
		stavka.setCena(stavkaCenovnikaDTO.getCena());
		
		stavkaCenovnikaService.save(stavka);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
