package klinika.controller;

import java.util.ArrayList;
import java.util.Date;
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

import klinika.dto.MedicinskoOsobljeDTO;
import klinika.dto.OdsustvoDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Odsustvo;
import klinika.service.AdminKlinikeService;
import klinika.service.EmailService;
import klinika.service.OdsustvoService;

@RestController
@RequestMapping(value = "odsustva")
public class OdsustvoController {

	@Autowired
	private OdsustvoService service;

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private EmailService mailService;
	
	private static String potvrdaZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je prihvacen.";
	private static String odbijanjeZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je odbijen. Razlog odbijanja je sljedeci: \n";
	private static String naslov = "Odgovor na zahtjev za odsustvom";


	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	@GetMapping(value = "/ucitajSvaNeodgovorenaOdsustva/{id}")
	public ResponseEntity<List<OdsustvoDTO>> ucitajSvaNeodgovorenaOdsustva(@PathVariable("id") long id) {
		AdminKlinike kor = adminKlinikeService.findOne(id);
		Klinika k = kor.getKlinika();
		ArrayList<Odsustvo> odsustva = (ArrayList<Odsustvo>) service.findByOdgovorenoFalseAndKlinikaIDAndPocetakAfter(k.getId(), new Date().getTime());
		ArrayList<OdsustvoDTO> odsustvaDTO = new ArrayList<>();
		for (Odsustvo odsustvo : odsustva) {
			OdsustvoDTO ods = new OdsustvoDTO();
			ods.setId(odsustvo.getId());
			ods.setKraj(odsustvo.getKraj());
			ods.setObrazlozenje(odsustvo.getObrazlozenje());
			ods.setOdgovoreno(odsustvo.isOdgovoreno());
			ods.setOdobreno(odsustvo.isOdobreno());
			ods.setPocetak(odsustvo.getPocetak());
			ods.setPodnosilac(new MedicinskoOsobljeDTO(odsustvo.getPodnosilac().getId(),
					odsustvo.getPodnosilac().getIme(), odsustvo.getPodnosilac().getPrezime()));
			odsustvaDTO.add(ods);
		}
		return new ResponseEntity<>(odsustvaDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	@PutMapping(value = "/updateOdsustvo", consumes = "application/json")
	public ResponseEntity<Boolean> updateOdsustvo(@RequestBody OdsustvoDTO odsustvoDTO) {
		try{
			boolean odgovor = service.azurirajOdsustvo(odsustvoDTO);
			if(odgovor) {
				Odsustvo odsustvo =service.findOne(odsustvoDTO.getId());
				try {
				mailService.posaljiEmail(odsustvo.getPodnosilac().getEmail(), naslov,
						odsustvo.isOdobreno() ? potvrdaZahtjeva : odbijanjeZahtjeva + odsustvo.getObrazlozenje());
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}

	}

}
