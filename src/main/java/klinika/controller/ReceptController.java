package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.ReceptDTO;
import klinika.model.Recept;
import klinika.service.ReceptService;

@RestController
@RequestMapping(value = "recepti")
public class ReceptController {

	@Autowired
	ReceptService receptService;

	@GetMapping(value = "/ucitajNeoverene")
	@PreAuthorize("hasRole('MED_SESTRA')")
	public ResponseEntity<List<ReceptDTO>> ucitajNeoverene() {
		List<Recept> recepti = receptService.findByMedSestraIdIsNull();

		List<ReceptDTO> receptDTO = new ArrayList<>();
		for (Recept r : recepti) {
			receptDTO.add(new ReceptDTO(r));
		}
		return new ResponseEntity<>(receptDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/overi", consumes = "application/json")
	@PreAuthorize("hasRole('MED_SESTRA')")
	public ResponseEntity<Boolean> overi(@RequestBody ReceptDTO receptDTO) {
		try {
			return new ResponseEntity<>(receptService.overi(receptDTO), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}

}
