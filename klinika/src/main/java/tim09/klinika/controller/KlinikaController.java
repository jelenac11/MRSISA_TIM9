package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.service.KlinikaService;

@RestController
@RequestMapping(value = "klinike")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<KlinikaDTO> ucitajPoId(@PathVariable Long id) {
		Klinika k = klinikaService.findOne(id);
		KlinikaDTO kl = new KlinikaDTO(k);
		return new ResponseEntity<KlinikaDTO>(kl, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<KlinikaDTO>> ucitajSveKlinike() {
		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikaDTO.add(new KlinikaDTO(k));
		}
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<KlinikaDTO> kreirajKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		Klinika klinika = new Klinika();
		
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setLokacija(klinikaDTO.getLokacija());

		klinika = klinikaService.save(klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
}
