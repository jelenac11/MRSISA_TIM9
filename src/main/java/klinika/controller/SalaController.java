package klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.RadniKalendarDTO;
import klinika.dto.SalaDTO;
import klinika.dto.SlobodanTerminDTO;
import klinika.dto.SlobodanTerminOperacijaDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Sala;
import klinika.repository.SalaRepository;
import klinika.service.AdminKlinikeService;
import klinika.service.OperacijaService;
import klinika.service.PregledService;
import klinika.service.SalaService;

@RestController
@RequestMapping(value = "sale")
public class SalaController {

	@Autowired
	private SalaService salaService;
	
	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private PregledService pregledService;

	@Autowired
	private OperacijaService operacijaService;

	@GetMapping(value = "/ucitajSve/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> ucitajSveSale(@PathVariable Long id) {
		
		List<Sala> sale = salaRepository.findAllByKlinikaIdAndAktivan(adminKlinikeService.findOne(id).getKlinika().getId(),true);

		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala s : sale) {
			salaDTO.add(new SalaDTO(s));
		}
		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izmenaSale", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izmenaSale(@RequestBody SalaDTO salaDTO) {
		Sala sala = salaService.findOne(salaDTO.getId());
		if (sala == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		try {
			boolean uspesno = salaService.update(salaDTO, sala);
			return new ResponseEntity<>(uspesno, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> kreirajSalu(@RequestBody SalaDTO salaDTO) {
		Sala sala = new Sala();
		sala.setBroj(salaDTO.getBroj());
		sala.setNaziv(salaDTO.getNaziv());
		sala.setAktivan(true);
		sala = salaService.save(sala);
		return new ResponseEntity<>(new SalaDTO(sala), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
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
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izbrisiSalu(@PathVariable Long id) {
		Sala sala = salaService.findOne(id);
		boolean odg;
		if (sala != null) {
			try {
				odg = salaService.remove(id);
			}
			catch (Exception e) {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND); 
			}
			return new ResponseEntity<>(odg, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/proveriBroj/{broj}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> proveriBroj(@PathVariable("broj") int broj) {
		Sala sala = salaService.findByBroj(broj);
		if (sala != null) {
			return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(false, HttpStatus.CREATED);
	}

	@PostMapping(value = "dobaviSlobodneSaleZaPregled", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> dobaviSlobodneSaleZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO) {
		AdminKlinike admin = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k = admin.getKlinika();
		List<Sala> sale = salaService.findByIdKlinikaAndVreme(k.getId(), slobodanTerminDTO.getDatumiVreme());
		List<SalaDTO> saleDTO = new ArrayList<>();
		for (Sala sala : sale) {
			saleDTO.add(new SalaDTO(sala));
		}
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}

	@PostMapping(value = "dobaviSlobodneSaleZaOperaciju", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> dobaviSlobodneSaleZaOperaciju(
			@RequestBody SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		AdminKlinike admin = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k = admin.getKlinika();
		List<Sala> sale = salaService.findByIdKlinikaAndVreme(k.getId(), slobodanTerminDTO.getDatumiVreme());
		List<SalaDTO> saleDTO = new ArrayList<>();
		for (Sala sala : sale) {
			saleDTO.add(new SalaDTO(sala));
		}
		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/dobaviRadniKalendar/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<RadniKalendarDTO>> dobaviRadniKalendar(@PathVariable("id") long id) {
		Sala sala = salaService.findOne(id);
		if (sala == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		List<RadniKalendarDTO> listOne = pregledService.kreirajRadniKalendar(sala.getId(), new Date().getTime());
		List<RadniKalendarDTO> listTwo = operacijaService.kreirajRadniKalendar(sala.getId(), new Date().getTime());
		listOne.addAll(listTwo);
		return new ResponseEntity<>(listOne, HttpStatus.CREATED);
	}
}
