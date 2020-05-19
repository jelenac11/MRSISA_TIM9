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

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.OcenaKlinikeDTO;
import tim09.klinika.dto.OcenaLekaraDTO;
import tim09.klinika.model.OcenaKlinike;
import tim09.klinika.model.OcenaLekara;
import tim09.klinika.service.KlinikaService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.LekarService;
import tim09.klinika.service.OcenaService;
import tim09.klinika.service.PacijentService;

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
			return new ResponseEntity<OcenaKlinikeDTO>(new OcenaKlinikeDTO(ocena), HttpStatus.OK);
		}
		return new ResponseEntity<OcenaKlinikeDTO>(new OcenaKlinikeDTO(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/ucitajOcenuPacijentaLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<OcenaLekaraDTO> ucitajOcenuPacijentaLekara(@RequestBody LekarDTO lekar) {
		OcenaLekara ocena = ocenaService.ucitajOcenuPacijentaLekara(lekar.getId());
		if (ocena != null) {
			return new ResponseEntity<OcenaLekaraDTO>(new OcenaLekaraDTO(ocena), HttpStatus.OK);
		}
		return new ResponseEntity<OcenaLekaraDTO>(new OcenaLekaraDTO(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/oceniKliniku", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> oceniKliniku(@RequestBody OcenaKlinikeDTO ocenaDTO) {
		OcenaKlinike ocena = ocenaService.ucitajOcenuPacijentaKlinike(ocenaDTO.getKlinika().getId());
		if (ocena != null) {
			ocena.setVrednost(ocenaDTO.getVrednost());
			ocenaService.save(ocena);
			klinikaService.izracunajProsek(ocenaDTO.getKlinika().getId());
		} else {
			ocena = new OcenaKlinike();
			ocena.setKlinika(klinikaService.findOne(ocenaDTO.getKlinika().getId()));
			ocena.setOcenjivac(pacijentService.findOne(ocenaDTO.getOcenjivac().getId()));
			ocena.setVrednost(ocenaDTO.getVrednost());
			ocenaService.save(ocena);
			klinikaService.dodajOcenu(ocena);
			klinikaService.izracunajProsek(ocenaDTO.getKlinika().getId());
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@PostMapping(value = "/oceniLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> oceniLekara(@RequestBody OcenaLekaraDTO ocenaDTO) {
		OcenaLekara ocena = ocenaService.ucitajOcenuPacijentaLekara(ocenaDTO.getLekar().getId());
		if (ocena != null) {
			ocena.setVrednost(ocenaDTO.getVrednost());
			ocenaService.save(ocena);
			lekarService.izracunajProsek(ocenaDTO.getLekar().getId());
		} else {
			ocena = new OcenaLekara();
			ocena.setLekar(lekarService.findOne(ocenaDTO.getLekar().getId()));
			ocena.setOcenjivac(pacijentService.findOne(ocenaDTO.getOcenjivac().getId()));
			ocena.setVrednost(ocenaDTO.getVrednost());
			ocenaService.save(ocena);
			lekarService.dodajOcenu(ocena);
			lekarService.izracunajProsek(ocenaDTO.getLekar().getId());
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
