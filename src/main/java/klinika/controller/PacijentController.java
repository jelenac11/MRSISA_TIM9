package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.PacijentDTO;
import klinika.model.Pacijent;
import klinika.service.PacijentService;

@RestController
@RequestMapping(value = "pacijenti")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'LEKAR', 'MED_SESTRA', 'PACIJENT')")
	public ResponseEntity<PacijentDTO> ucitajPoId(@PathVariable Long id) {
		Pacijent p = pacijentService.findOne(id);
		PacijentDTO pac = new PacijentDTO(p);
		return new ResponseEntity<>(pac, HttpStatus.OK);
	}

	@GetMapping(value = "/vratiRegZahteve")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<List<PacijentDTO>> vratiRegZahteve() {
		List<Pacijent> pacijenti = pacijentService.nadjiRegZahteve();
		List<PacijentDTO> pacijentDTO = new ArrayList<>();
		for (Pacijent p : pacijenti) {
			pacijentDTO.add(new PacijentDTO(p));
		}
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<PacijentDTO> promeniKorisnika(@RequestBody PacijentDTO pDTO) {

		Pacijent p = pacijentService.findOne(pDTO.getId());
		if (p == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		p.setAdresa(pDTO.getAdresa());
		p.setDrzava(pDTO.getDrzava());
		p.setGrad(pDTO.getGrad());
		p.setIme(pDTO.getIme());
		p.setPrezime(pDTO.getPrezime());
		p.setBrojTelefona(pDTO.getBrojTelefona());
		p.setAktiviran(pDTO.isAktiviran());
		p.setPromenjenaLozinka(pDTO.isPromenjenaLozinka());
		p.setVerifikovan(pDTO.isVerifikovan());

		p = pacijentService.save(p);
		return new ResponseEntity<>(new PacijentDTO(p), HttpStatus.OK);
	}

	@GetMapping("dobaviSvePoIdKlinike/{id}")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<PacijentDTO>> dobaviSvePoIdKlinike(@PathVariable Long id) {
		List<PacijentDTO> p = pacijentService.findByKlinikaID(id);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
}
