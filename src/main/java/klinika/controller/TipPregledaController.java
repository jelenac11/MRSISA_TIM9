package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.TipPregledaDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.StavkaCenovnika;
import klinika.model.TipPregleda;
import klinika.service.AdminKlinikeService;
import klinika.service.KlinikaService;
import klinika.service.TipPregledaService;

@RestController
@RequestMapping(value = "tipoviPregleda")
public class TipPregledaController {

	@Autowired
	private TipPregledaService tipPregledaService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private KlinikaService klinikaService;

	@GetMapping(value = "/ucitajSvePoIdKlinike/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<TipPregledaDTO>> ucitajSveTipovePregledaPoIDKlinike(@PathVariable("id") long id) {
		AdminKlinike adm = adminKlinikeService.findOne(id);
		Klinika k = adm.getKlinika();
		List<TipPregleda> tipoviPregleda = tipPregledaService.findAllByKlinikaId(k.getId());

		List<TipPregledaDTO> tipoviPregledaDTO = new ArrayList<>();
		for (TipPregleda t : tipoviPregleda) {
			tipoviPregledaDTO.add(new TipPregledaDTO(t));
		}
		return new ResponseEntity<>(tipoviPregledaDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<TipPregledaDTO> kreirajTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO) {
		TipPregleda pregled = tipPregledaService.findOneByNaziv(tipPregledaDTO.getNaziv());
		if (pregled != null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		TipPregleda tipPregleda = new TipPregleda();
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setOpis(tipPregledaDTO.getOpis());
		tipPregleda.setAktivan(true);
		Klinika k = klinikaService.findByNaziv(tipPregledaDTO.getKlinika());
		tipPregleda.setKlinika(k);

		StavkaCenovnika stavka = new StavkaCenovnika();
		stavka.setCena(tipPregledaDTO.getCena());
		stavka.setTipPregleda(tipPregleda);
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		AdminKlinike ulogovan = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		stavka.setCenovnik(ulogovan.getKlinika().getCenovnik());

		tipPregleda.setStavkaCenovnika(stavka);

		tipPregleda = tipPregledaService.save(tipPregleda);

		return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.CREATED);
	}

	@GetMapping(value = "/provjeraPostojanostiImena/{naziv}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> provjeraPostojanostiImena(@PathVariable("naziv") String naziv) {
		TipPregleda pregled = tipPregledaService.findOneByNaziv(naziv);
		if (pregled == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PostMapping(value = "/izbrisiTipPregleda", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izbrisiTipPregleda(@RequestBody TipPregledaDTO tipPregleda) {
		TipPregleda pregled = tipPregledaService.findOneByNaziv(tipPregleda.getNaziv());
		if (pregled == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}

		boolean uspesno = tipPregledaService.remove(pregled.getId());

		return new ResponseEntity<>(uspesno, HttpStatus.OK);
	}

	@PostMapping(value = "/izmenaTipaPregleda", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izmenaTipaPregleda(@RequestBody TipPregledaDTO tipPregledaDTO) {
		TipPregleda pregled = tipPregledaService.findOne(tipPregledaDTO.getId());
		if (pregled == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		boolean uspesno = tipPregledaService.update(tipPregledaDTO, pregled);
		return new ResponseEntity<>(uspesno, HttpStatus.OK);
	}

	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<TipPregledaDTO>> ucitajSveTipove() {
		List<TipPregleda> tipovi = tipPregledaService.findAll();
		List<TipPregledaDTO> tipoviDTO = new ArrayList<>();

		for (TipPregleda tp : tipovi) {
			tipoviDTO.add(new TipPregledaDTO(tp));
		}
		return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/ucitajTipoveKlinike/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<TipPregledaDTO>> ucitajTipovePregledaKlinike(@PathVariable("id") long id) {
		return new ResponseEntity<>(tipPregledaService.vratiTipoveKlinike(id), HttpStatus.OK);
	}
}
