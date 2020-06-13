package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.PretragaKlinikeDTO;
import klinika.dto.StavkaCenovnikaDTO;
import klinika.model.StavkaCenovnika;
import klinika.service.StavkaCenovnikaService;

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
	public ResponseEntity<?> izmeni(@RequestBody StavkaCenovnikaDTO stavkaCenovnikaDTO) {
		StavkaCenovnika stavka = stavkaCenovnikaService.findOne(stavkaCenovnikaDTO.getId());
		stavka.setCena(stavkaCenovnikaDTO.getCena());

		try {
			stavkaCenovnikaService.save(stavka);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
