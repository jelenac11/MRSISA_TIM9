package tim09.klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.dto.ZdravstveniKartonDTO;
import tim09.klinika.model.ZdravstveniKarton;
import tim09.klinika.service.ZdravstveniKartonService;

@RestController
@RequestMapping(value = "zdravstveniKartoni")
public class ZdravstveniKartonController {

	@Autowired
	private ZdravstveniKartonService zdravstveniKartonService;
	
	@PutMapping(value = "/dobaviKartonPacijenta", consumes = "application/json")
	public ResponseEntity<ZdravstveniKartonDTO> dobaviKartonPacijenta(@RequestBody PacijentDTO p) {
		ZdravstveniKarton z = zdravstveniKartonService.findOne(p.getId());
		return ResponseEntity.ok(new ZdravstveniKartonDTO(z));
	}
}
