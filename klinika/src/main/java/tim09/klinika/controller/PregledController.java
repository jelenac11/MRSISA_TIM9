package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.service.PregledService;

@RestController
@RequestMapping(value = "pregledi")
public class PregledController {

	@Autowired
	private PregledService pregledService;
	
	@PostMapping(value="dodajSlobodanTerminZaPregled",consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodajSlobodanTerminZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO){
		boolean uspesno=pregledService.insertPregled(slobodanTerminDTO);
		return new ResponseEntity<Boolean>(uspesno,HttpStatus.OK);
	}
}
