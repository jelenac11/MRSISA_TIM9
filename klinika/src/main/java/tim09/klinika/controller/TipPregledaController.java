package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.service.TipPregledaService;

@RestController
@RequestMapping(value = "tipoviPregleda")
public class TipPregledaController {

	@Autowired
	TipPregledaService tipPregledaService;
	
	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<TipPregledaDTO>> ucitajSveTipovePregleda() {
		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();

		List<TipPregledaDTO> tipoviPregledaDTO = new ArrayList<>();
		for (TipPregleda t : tipoviPregleda) {
			tipoviPregledaDTO.add(new TipPregledaDTO(t.getId(),t.getNaziv(),t.getOpis()));
		}
		return new ResponseEntity<>(tipoviPregledaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<TipPregleda> kreirajTipPregleda(@RequestBody TipPregleda tipPregleda) {
		TipPregleda pregled=tipPregledaService.findOneByNaziv(tipPregleda.getNaziv());
		if(pregled!=null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		tipPregleda = tipPregledaService.save(tipPregleda);
		return new ResponseEntity<>(tipPregleda, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/provjeraPostojanostiImena/{naziv}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> provjeraPostojanostiImena(@PathVariable("naziv") String naziv) {
		TipPregleda pregled=tipPregledaService.findOneByNaziv(naziv);
		if(pregled==null) {
			return new ResponseEntity<>(false,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	
	
}
