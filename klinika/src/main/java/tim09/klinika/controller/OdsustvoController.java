package tim09.klinika.controller;

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

import tim09.klinika.dto.MedicinskoOsobljeDTO;
import tim09.klinika.dto.OdsustvoDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.OdsustvoService;

@RestController
@RequestMapping(value = "odsustva")
public class OdsustvoController {

	@Autowired
	private OdsustvoService service;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	@GetMapping(value = "/ucitajSvaNeodgovorenaOdsustva/{id}")
	public ResponseEntity<List<OdsustvoDTO>> ucitajSvaNeodgovorenaOdsustva(@PathVariable("id") long id) {
		AdminKlinike kor=adminKlinikeService.findOne(id);
		Klinika k=kor.getKlinika();
		ArrayList<Odsustvo> odsustva = (ArrayList<Odsustvo>) service.findByOdgovorenoFalseAndKlinikaID(k.getId());
		ArrayList<OdsustvoDTO> odsustvaDTO = new ArrayList<OdsustvoDTO>();
		for (Odsustvo odsustvo : odsustva) {
			OdsustvoDTO ods=new OdsustvoDTO();
			ods.setId(odsustvo.getId());
			ods.setKraj(odsustvo.getKraj());
			ods.setObrazlozenje(odsustvo.getObrazlozenje());
			ods.setOdgovoreno(odsustvo.isOdgovoreno());
			ods.setOdobreno(odsustvo.isOdobreno());
			ods.setPocetak(odsustvo.getPocetak());
			ods.setPodnosilac(new MedicinskoOsobljeDTO(odsustvo.getPodnosilac().getId(),odsustvo.getPodnosilac().getIme(),odsustvo.getPodnosilac().getPrezime()));
			odsustvaDTO.add(ods);
		}
		return new ResponseEntity<>(odsustvaDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	@PutMapping(value="/updateOdsustvo",consumes = "application/json")
	public ResponseEntity<OdsustvoDTO> updateOdsustvo(@RequestBody OdsustvoDTO odsustvoDTO) {
		Odsustvo odsustvo=service.findOne(odsustvoDTO.getId());
		if(odsustvo==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		odsustvo.setObrazlozenje(odsustvoDTO.getObrazlozenje());
		odsustvo.setOdgovoreno(odsustvoDTO.isOdgovoreno());
		odsustvo.setOdobreno(odsustvoDTO.isOdobreno());
		
		odsustvo = service.updateOdsustvo(odsustvo);
		
		odsustvoDTO.setId(odsustvo.getId());
		odsustvoDTO.setKraj(odsustvo.getKraj());
		odsustvoDTO.setObrazlozenje(odsustvo.getObrazlozenje());
		odsustvoDTO.setOdgovoreno(odsustvo.isOdgovoreno());
		odsustvoDTO.setOdobreno(odsustvo.isOdobreno());
		odsustvoDTO.setPocetak(odsustvo.getPocetak());
		odsustvoDTO.setPodnosilac(new MedicinskoOsobljeDTO(odsustvoDTO.getPodnosilac().getId(),odsustvoDTO.getPodnosilac().getIme(),odsustvoDTO.getPodnosilac().getPrezime()));
		
		return new ResponseEntity<>(odsustvoDTO, HttpStatus.OK);
	}

	
}
