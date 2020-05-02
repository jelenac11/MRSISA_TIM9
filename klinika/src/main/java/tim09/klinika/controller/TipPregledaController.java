package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.KlinikaService;
import tim09.klinika.service.PregledService;
import tim09.klinika.service.TipPregledaService;

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
	public ResponseEntity<List<TipPregledaDTO>> ucitajSveTipovePregledaPoIDKlinike(@PathVariable("id") long id) {
		AdminKlinike adm=adminKlinikeService.findOne(id);
		Klinika k=adm.getKlinika();
		List<TipPregleda> tipoviPregleda = tipPregledaService.findAllByKlinikaId(k.getId());

		List<TipPregledaDTO> tipoviPregledaDTO = new ArrayList<>();
		for (TipPregleda t : tipoviPregleda) {
			tipoviPregledaDTO.add(new TipPregledaDTO(t.getId(),t.getNaziv(),t.getOpis()));
		}
		return new ResponseEntity<>(tipoviPregledaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<TipPregledaDTO> kreirajTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO) {
		TipPregleda pregled=tipPregledaService.findOneByNaziv(tipPregledaDTO.getNaziv());
		if(pregled!=null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		TipPregleda tipPregleda=new TipPregleda();
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setOpis(tipPregledaDTO.getOpis());
		Klinika k=klinikaService.findByNaziv(tipPregledaDTO.getKlinika());
		tipPregleda.setKlinika(k);
		
		tipPregleda = tipPregledaService.save(tipPregleda);
		
		return new ResponseEntity<>(new TipPregledaDTO(tipPregleda), HttpStatus.CREATED);
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
	
	@PostMapping(value="/izbrisiTipPregleda",consumes = "application/json")
	public ResponseEntity<Boolean> izbrisiTipPregleda(@RequestBody TipPregledaDTO tipPregleda) {
		TipPregleda pregled=tipPregledaService.findOneByNaziv(tipPregleda.getNaziv());
		if(pregled==null) {
			return new ResponseEntity<>(false,HttpStatus.OK);
		}
		boolean uspesno=tipPregledaService.remove(pregled.getId());
		return new ResponseEntity<>(uspesno,HttpStatus.OK);
	}
	
	@PostMapping(value="/izmenaTipaPregleda",consumes="application/json")
	public ResponseEntity<Boolean> izmenaTipaPregleda(@RequestBody TipPregledaDTO tipPregledaDTO){
		TipPregleda pregled=tipPregledaService.findOne(tipPregledaDTO.getId());
		if(pregled==null) {
			return new ResponseEntity<>(false,HttpStatus.OK);
		}
		boolean uspesno=tipPregledaService.update(tipPregledaDTO,pregled);
		return new ResponseEntity<Boolean>(uspesno,HttpStatus.OK);
	}
	
	
}
