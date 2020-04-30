package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.SifrarnikDTO;
import tim09.klinika.model.Sifrarnik;
import tim09.klinika.service.SifrarnikService;

@RestController
@RequestMapping(value = "sifrarnici")
public class SifrarnikController {

	@Autowired
	private SifrarnikService sifrarnikService;

	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<SifrarnikDTO>> ucitajSveSifrarnike() {
		List<Sifrarnik> sifrarnici = sifrarnikService.findAll();

		List<SifrarnikDTO> sifrarnikDTO = new ArrayList<>();
		for (Sifrarnik s : sifrarnici) {
			sifrarnikDTO.add(new SifrarnikDTO(s));
		}
		return new ResponseEntity<>(sifrarnikDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<SifrarnikDTO> kreirajSifrarnik(@RequestBody SifrarnikDTO sifrarnikDTO) {
		Sifrarnik sifrarnik = new Sifrarnik();
		sifrarnik.setNaziv(sifrarnikDTO.getNaziv());
		sifrarnik.setTipSifrarnika(sifrarnikDTO.getTipSifrarnika());

		sifrarnik = sifrarnikService.save(sifrarnik);
		return new ResponseEntity<>(new SifrarnikDTO(sifrarnik), HttpStatus.CREATED);
	}
}
