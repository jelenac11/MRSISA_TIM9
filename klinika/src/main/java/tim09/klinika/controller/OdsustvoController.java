package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.MedicinskoOsobljeDTO;
import tim09.klinika.dto.OdsustvoDTO;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.service.OdsustvoService;

@RestController
@RequestMapping(value = "odsustva")
public class OdsustvoController {

	@Autowired
	private OdsustvoService service;
	
	@GetMapping(value = "/ucitajSvaNeodgovorenaOdsustva")
	public ResponseEntity<List<OdsustvoDTO>> ucitajSvaNeodgovorenaOdsustva() {
		ArrayList<Odsustvo> odsustva = (ArrayList<Odsustvo>) service.findByOdgovoreno(false);
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
