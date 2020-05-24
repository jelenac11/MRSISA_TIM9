package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.StavkaSifrarnikaDTO;
import tim09.klinika.model.Sifrarnik;
import tim09.klinika.model.StavkaSifrarnika;
import tim09.klinika.service.SifrarnikService;
import tim09.klinika.service.StavkaSifrarnikaService;

@RestController
@RequestMapping(value = "stavkeSifrarnika")
public class StavkaSifrarnikaController {

	@Autowired
	StavkaSifrarnikaService stavkaSifrarnikaService;
	
	@Autowired
	SifrarnikService sifrarnikService;
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<StavkaSifrarnika> kreirajStavku(@RequestBody StavkaSifrarnikaDTO stavkaDTO) {
		StavkaSifrarnika stavka = new StavkaSifrarnika();
		
		stavka.setSifra(stavkaDTO.getSifra());
		stavka.setNaziv(stavkaDTO.getNaziv());
		stavka.setTipSifre(stavkaDTO.getTipSifre());
		Sifrarnik s = sifrarnikService.findByTipSifrarnika(stavkaDTO.getTipSifre());
		stavka.setSifrarnik(s);
		s.getStavke().add(stavka);
		
		sifrarnikService.save(s);
		return new ResponseEntity<>(stavka, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/proveriSifru/{sifra}")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<Boolean> proveriSifru(@PathVariable("sifra") String sifra) {
		StavkaSifrarnika stavka = stavkaSifrarnikaService.findBySifra(sifra);
		if (stavka != null) {
			return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(false, HttpStatus.CREATED);
	}
}
