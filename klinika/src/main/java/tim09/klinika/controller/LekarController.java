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
import tim09.klinika.model.Autoritet;
import tim09.klinika.model.Lekar;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.LekarService;

@RestController
@RequestMapping(value = "lekari")
public class LekarController {

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AutoritetService autoritetService;
	
	@GetMapping("/{id}")
	public ResponseEntity<LekarDTO> ucitajPoId(@PathVariable Long id) {
		Lekar l = lekarService.findOne(id);
		l.getKlinika();
		LekarDTO le = new LekarDTO(l);
		return new ResponseEntity<LekarDTO>(le, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<LekarDTO>> ucitajSveLekare() {
		List<Lekar> lekari = lekarService.findAll();

		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekarDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<LekarDTO> kreirajLekara(@RequestBody LekarDTO lekarDTO) {
		Lekar lekar = new Lekar();
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setIme(lekarDTO.getIme());
		lekar.setLozinka(korisnikService.encodePassword(lekarDTO.getLozinka()));
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		lekar.setPocetakRadnogVremena(0);
		lekar.setKrajRadnogVremena(0);
		lekar.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		lekar.setAktiviran(true);
		lekar.setVerifikovan(true);
		lekar.setPromenjenaLozinka(false);

		lekar = lekarService.save(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}

}
