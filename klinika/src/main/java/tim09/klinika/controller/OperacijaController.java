package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.OperacijaDTO;
import tim09.klinika.dto.PregledDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.dto.SlobodanTerminOperacijaDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.OperacijaService;
import tim09.klinika.service.PregledService;

@RestController
@RequestMapping(value = "operacije")
public class OperacijaController {

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@GetMapping(value = "ucitajSveOperacijeNaCekanju/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveOperacijeNaCekanju(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Operacija> operacije = operacijaService.findByKlinikaIdAndSalaIdAndVremeAfter(admin.getKlinika().getId(),
				new Date().getTime());

		List<OperacijaDTO> operacijeDTO = new ArrayList<OperacijaDTO>();
		for (Operacija op : operacije) {
			operacijeDTO.add(new OperacijaDTO(op));
		}
		return new ResponseEntity<List<OperacijaDTO>>(operacijeDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "ucitajSveOperacijePacijenta/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveOperacijePacijenta(@PathVariable("id") long id) {
		return new ResponseEntity<>(operacijaService.vratiOperacijePacijenta(id), HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSveZakazaneOperacije/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveZakazaneOperacije(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Operacija> operacije = operacijaService.findByOtkazanaAndKlinikaIdAndVremeAfter(false,
				admin.getKlinika().getId(), new Date().getTime());

		List<OperacijaDTO> operacijeDTO = new ArrayList<OperacijaDTO>();
		for (Operacija operacija : operacije) {
			operacijeDTO.add(new OperacijaDTO(operacija));
		}
		return new ResponseEntity<List<OperacijaDTO>>(operacijeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "dodijeliSaluOperaciji", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodijeliSaluOperaciji(@RequestBody SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		operacijaService.dodijeliSalu(slobodanTerminDTO);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PutMapping(value = "otkaziOperacijuLekara")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> otkaziOperacijuLekara(@RequestBody PretragaLekaraDTO pldto) throws MailException, InterruptedException {
		return new ResponseEntity<>(operacijaService.otkaziOperacijuLekara(pldto), HttpStatus.OK);
	}
}
