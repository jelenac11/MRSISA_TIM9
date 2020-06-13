package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.PacijentDTO;
import klinika.dto.ZdravstveniKartonDTO;
import klinika.model.ZdravstveniKarton;
import klinika.service.ZdravstveniKartonService;

@RestController
@RequestMapping(value = "zdravstveniKartoni")
public class ZdravstveniKartonController {

	@Autowired
	private ZdravstveniKartonService zdravstveniKartonService;

	@PreAuthorize("hasAnyRole('PACIJENT', 'LEKAR', 'MED_SESTRA')")
	@PutMapping(value = "/dobaviKartonPacijenta", consumes = "application/json")
	public ResponseEntity<ZdravstveniKartonDTO> dobaviKartonPacijenta(@RequestBody PacijentDTO p) {
		ZdravstveniKarton z = zdravstveniKartonService.findOne(p.getId());
		return ResponseEntity.ok(new ZdravstveniKartonDTO(z));
	}

	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	@PutMapping(value = "/izmeniKarton", consumes = "application/json")
	public ResponseEntity<?> izmeniKarton(@RequestBody ZdravstveniKartonDTO zk) {
		ZdravstveniKarton z = zdravstveniKartonService.findOne(zk.getId());
		z.setDioptrija(zk.getDioptrija());
		z.setKrvnaGrupa(zk.getKrvnaGrupa());
		z.setTezina(zk.getTezina());
		z.setVisina(zk.getVisina());
		try {
			zdravstveniKartonService.save(z);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(true);
	}
}
