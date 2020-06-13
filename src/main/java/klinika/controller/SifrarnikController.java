package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.SifrarnikDTO;
import klinika.model.Sifrarnik;
import klinika.service.SifrarnikService;

@RestController
@RequestMapping(value = "sifrarnici")
public class SifrarnikController {

	@Autowired
	private SifrarnikService sifrarnikService;

	@GetMapping(value = "/ucitajSifrarnikLekova")
	public ResponseEntity<SifrarnikDTO> ucitajSifrarnikLekova() {
		Sifrarnik sifrarnik = sifrarnikService.findByTipSifrarnika("LEKOVI");

		SifrarnikDTO sifrarnikDTO = new SifrarnikDTO(sifrarnik);
		return new ResponseEntity<>(sifrarnikDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/ucitajSifrarnikDijagnoza")
	public ResponseEntity<SifrarnikDTO> ucitajSifrarnikDijagnoza() {
		Sifrarnik sifrarnik = sifrarnikService.findByTipSifrarnika("DIJAGNOZE");

		SifrarnikDTO sifrarnikDTO = new SifrarnikDTO(sifrarnik);
		return new ResponseEntity<>(sifrarnikDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> kreirajSifrarnik(@RequestBody SifrarnikDTO sifrarnikDTO) {
		Sifrarnik sifrarnik = new Sifrarnik();
		sifrarnik.setNaziv(sifrarnikDTO.getNaziv());
		sifrarnik.setTipSifrarnika(sifrarnikDTO.getTipSifrarnika());

		try {
			sifrarnik = sifrarnikService.save(sifrarnik);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SifrarnikDTO(sifrarnik), HttpStatus.CREATED);
	}
}
