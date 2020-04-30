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

import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.MedSestraDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.MedSestra;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KlinikaService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.MedSestraService;

@RestController
@RequestMapping(value = "sestre")
public class MedSestraController {

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private MedSestraService medicinskaSestraService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AutoritetService autoritetService;

	@GetMapping("/{id}")
	public ResponseEntity<MedSestraDTO> ucitajPoId(@PathVariable Long id) {
		MedSestra m = medicinskaSestraService.findOne(id);
		m.getKlinika();
		MedSestraDTO ms = new MedSestraDTO(m);
		return new ResponseEntity<MedSestraDTO>(ms, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<MedSestraDTO>> ucitajSveMedicinskeSestre() {
		List<MedSestra> medicinskeSestre = medicinskaSestraService.findAll();

		List<MedSestraDTO> medSestraDTO = new ArrayList<>();
		for (MedSestra m : medicinskeSestre) {
			medSestraDTO.add(new MedSestraDTO(m));
		}
		return new ResponseEntity<>(medSestraDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<MedSestraDTO> kreirajMedSestru(@RequestBody MedSestraDTO medSestraDTO) {
		MedSestra medSestra = new MedSestra();
		medSestra.setAdresa(medSestraDTO.getAdresa());
		medSestra.setDrzava(medSestraDTO.getDrzava());
		medSestra.setGrad(medSestraDTO.getGrad());
		medSestra.setEmail(medSestraDTO.getEmail());
		medSestra.setIme(medSestraDTO.getIme());
		medSestra.setLozinka(korisnikService.encodePassword(medSestraDTO.getLozinka()));
		medSestra.setPrezime(medSestraDTO.getPrezime());
		medSestra.setBrojTelefona(medSestraDTO.getBrojTelefona());
		medSestra.setPocetakRadnogVremena(0);
		medSestra.setKrajRadnogVremena(0);
		medSestra.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		medSestra.setAktiviran(true);
		medSestra.setVerifikovan(true);
		medSestra.setPromenjenaLozinka(false);
		Klinika k=klinikaService.findByNaziv(medSestraDTO.getKlinika());
		medSestra.setKlinika(k);
		
		medSestra = medicinskaSestraService.save(medSestra);
		return new ResponseEntity<>(new MedSestraDTO(medSestra), HttpStatus.CREATED);
	}

	
}
