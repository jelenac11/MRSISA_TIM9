package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.model.Pacijent;
import tim09.klinika.service.PacijentService;

@RestController
@RequestMapping(value = "pacijenti")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
	@GetMapping(value = "/vratiRegZahteve")
	public ResponseEntity<List<PacijentDTO>> vratiRegZahteve(){
		List<Pacijent> pacijenti = pacijentService.nadjiRegZahteve();
		List<PacijentDTO> pacijentDTO = new ArrayList<>();
		for (Pacijent p : pacijenti) {
			pacijentDTO.add(new PacijentDTO(p));
		}
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	
}
