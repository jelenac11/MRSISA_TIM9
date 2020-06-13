package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.KlinikaDTO;
import klinika.dto.LekarDTO;
import klinika.dto.OcenaKlinikeDTO;
import klinika.dto.OcenaLekaraDTO;
import klinika.model.OcenaKlinike;
import klinika.model.OcenaLekara;
import klinika.service.KlinikaService;
import klinika.service.LekarService;
import klinika.service.OcenaService;
import klinika.service.PacijentService;

@RestController
@RequestMapping(value = "ocene")
public class OcenaController {

	@Autowired
	OcenaService ocenaService;

	@Autowired
	KlinikaService klinikaService;

	@Autowired
	PacijentService pacijentService;

	@Autowired
	LekarService lekarService;

	@PostMapping(value = "/ucitajOcenuPacijentaKlinike", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<OcenaKlinikeDTO> ucitajOcenuPacijentaKlinike(@RequestBody KlinikaDTO klinika) {
		OcenaKlinike ocena = ocenaService.ucitajOcenuPacijentaKlinike(klinika.getId());
		if (ocena != null) {
			return new ResponseEntity<>(new OcenaKlinikeDTO(ocena), HttpStatus.OK);
		}
		return new ResponseEntity<>(new OcenaKlinikeDTO(), HttpStatus.OK);
	}

	@PostMapping(value = "/ucitajOcenuPacijentaLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<OcenaLekaraDTO> ucitajOcenuPacijentaLekara(@RequestBody LekarDTO lekar) {
		OcenaLekara ocena = ocenaService.ucitajOcenuPacijentaLekara(lekar.getId());
		if (ocena != null) {
			return new ResponseEntity<>(new OcenaLekaraDTO(ocena), HttpStatus.OK);
		}
		return new ResponseEntity<>(new OcenaLekaraDTO(), HttpStatus.OK);
	}

	@PostMapping(value = "/oceniKliniku", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<?> oceniKliniku(@RequestBody OcenaKlinikeDTO ocenaDTO) {
		OcenaKlinike ocena = ocenaService.ucitajOcenuPacijentaKlinike(ocenaDTO.getKlinika().getId());
		if (ocena != null) {
			ocena.setVrednost(ocenaDTO.getVrednost());
			try {
				ocenaService.save(ocena);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
			}
			klinikaService.izracunajProsek(ocenaDTO.getKlinika().getId());
		} else {
			ocena = new OcenaKlinike();
			ocena.setKlinika(klinikaService.findOne(ocenaDTO.getKlinika().getId()));
			ocena.setOcenjivac(pacijentService.findOne(ocenaDTO.getOcenjivac().getId()));
			ocena.setVrednost(ocenaDTO.getVrednost());
			try {
				ocenaService.save(ocena);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>("Database error!!", HttpStatus.BAD_REQUEST);
			}
			klinikaService.dodajOcenu(ocena);
			klinikaService.izracunajProsek(ocenaDTO.getKlinika().getId());
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PostMapping(value = "/oceniLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<?> oceniLekara(@RequestBody OcenaLekaraDTO ocenaDTO) {
		OcenaLekara ocena = ocenaService.ucitajOcenuPacijentaLekara(ocenaDTO.getLekar().getId());
		if (ocena != null) {
			ocena.setVrednost(ocenaDTO.getVrednost());
			try {
				ocenaService.save(ocena);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>("Database error!!!", HttpStatus.BAD_REQUEST);
			}
			lekarService.izracunajProsek(ocenaDTO.getLekar().getId());
		} else {
			ocena = new OcenaLekara();
			ocena.setLekar(lekarService.findOne(ocenaDTO.getLekar().getId()));
			ocena.setOcenjivac(pacijentService.findOne(ocenaDTO.getOcenjivac().getId()));
			ocena.setVrednost(ocenaDTO.getVrednost());
			try {
				ocenaService.save(ocena);
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>("Database Error!", HttpStatus.BAD_REQUEST);
			}
			lekarService.dodajOcenu(ocena);
			lekarService.izracunajProsek(ocenaDTO.getLekar().getId());
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
