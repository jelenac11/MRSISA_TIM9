package tim09.klinika.controller;

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

import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.dto.SalaDTO;
import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Sala;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.OperacijaService;
import tim09.klinika.service.PregledService;
import tim09.klinika.service.SalaService;

@RestController
@RequestMapping(value = "sale")
public class SalaController {

	@Autowired
	private SalaService salaService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private PregledService pregledService;

	@Autowired
	private OperacijaService operacijaService;

	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> ucitajSveSale() {
		List<Sala> sale = salaService.findAll();

		List<SalaDTO> salaDTO = new ArrayList<>();
		for (Sala s : sale) {
			salaDTO.add(new SalaDTO(s));
		}
		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> kreirajSalu(@RequestBody SalaDTO salaDTO) {
		Sala sala = new Sala();
		sala.setBroj(salaDTO.getBroj());
		sala.setNaziv(salaDTO.getNaziv());

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
	public ResponseEntity<Void> izbrisiSalu(@PathVariable Long id) {
		Sala sala = salaService.findOne(id);

		if (sala != null) {
			salaService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
	
	@PostMapping(value="dobaviSlobodneSaleZaPregled",consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> dobaviSlobodneSaleZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO){
		AdminKlinike admin=adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k=admin.getKlinika();
		List<Sala> sale=salaService.findByIdKlinikaAndVremeAndTipPregleda(k.getId(),slobodanTerminDTO.getDatumiVreme(),slobodanTerminDTO.getTipPregleda(),slobodanTerminDTO.getTrajanje());
		List<SalaDTO> saleDTO=new ArrayList<SalaDTO>();
		for(Sala sala:sale) {
			saleDTO.add(new SalaDTO(sala));
		}
		return new ResponseEntity<List<SalaDTO>>(saleDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/dobaviRadniKalendar/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<RadniKalendarDTO>> dobaviRadniKalendar(@PathVariable("id") long id) {
		Sala sala = salaService.findOne(id);
		if (sala == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		List<RadniKalendarDTO>listOne= pregledService.kreirajRadniKalendar(sala.getId(),new Date().getTime());
		List<RadniKalendarDTO>listTwo=operacijaService.kreirajRadniKalendar(sala.getId(),new Date().getTime());
		listOne.addAll(listTwo);
		return new ResponseEntity<>(listOne, HttpStatus.CREATED);
	}
}
