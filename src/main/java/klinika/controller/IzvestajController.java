package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.IzvestajDTO;
import klinika.service.IzvestajService;

@RestController
@RequestMapping(value = "izvestaji")
public class IzvestajController {

	@Autowired
	private IzvestajService izvestajService;

	@PostMapping(value = "dodajIzvestaj", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> dodajIzvestaj(@RequestBody IzvestajDTO izvestajDTO) {
		return new ResponseEntity<>(izvestajService.dodajIzvestaj(izvestajDTO), HttpStatus.OK);
	}

	@PostMapping(value = "izmeniIzvestaj", consumes = "application/json")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<Boolean> izmeniIzvestaj(@RequestBody IzvestajDTO izvestajDTO) {
		return new ResponseEntity<>(izvestajService.izmeniIzvestaj(izvestajDTO), HttpStatus.OK);
	}
}
