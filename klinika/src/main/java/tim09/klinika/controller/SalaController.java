package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.SalaDTO;
import tim09.klinika.model.Sala;
import tim09.klinika.service.SalaService;

@RestController
@RequestMapping(value = "sale")
public class SalaController {

	@Autowired
	private SalaService salaService;

	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<SalaDTO>> ucitajSveSale() {
		List<Sala> sale = salaService.findAll();

		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala s : sale) {
			salaDTO.add(new SalaDTO(s));
		}
		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<SalaDTO> kreirajSalu(@RequestBody SalaDTO salaDTO) {
		Sala sala = new Sala();
		sala.setBroj(salaDTO.getBroj());
		sala.setNaziv(salaDTO.getNaziv());

		sala = salaService.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<SalaDTO> promeniSalu(@RequestBody SalaDTO salaDTO) {
		Sala sala = salaService.findOne(salaDTO.getId());
		if (sala == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		sala.setBroj(salaDTO.getBroj());
		sala.setNaziv(salaDTO.getNaziv());
		
		sala = salaService.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> izbrisiSalu(@PathVariable Long id) {
		Sala sala = salaService.findOne(id);

		if (sala != null) {
			salaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
