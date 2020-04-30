package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.MedSestraDTO;
import tim09.klinika.model.MedSestra;
import tim09.klinika.service.MedSestraService;

@RestController
@RequestMapping(value = "sestre")
public class MedSestraController {

	@Autowired
	private MedSestraService medSestraService;

	@GetMapping("/{id}")
	public ResponseEntity<MedSestraDTO> ucitajPoId(@PathVariable Long id) {
		MedSestra m = medSestraService.findOne(id);
		MedSestraDTO ms = new MedSestraDTO(m);
		return new ResponseEntity<MedSestraDTO>(ms, HttpStatus.OK);
	}

}
