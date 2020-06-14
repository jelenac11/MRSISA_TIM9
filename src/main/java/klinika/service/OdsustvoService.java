package klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.OdsustvoDTO;
import klinika.dto.RadniKalendarDTO;
import klinika.model.Odsustvo;
import klinika.repository.OdsustvoRepository;

@Service
public class OdsustvoService {

	private static String potvrdaZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je prihvacen.";
	private static String odbijanjeZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je odbijen. Razlog odbijanja je sljedeci: \n";
	private static String naslov = "Odgovor na zahtjev za odsustvom";

	@Autowired
	private OdsustvoRepository odsustvoRepository;

	@Autowired
	EmailService mailService;

	@Transactional(readOnly = true)
	public Odsustvo findOne(Long id) {
		return odsustvoRepository.findById(id).orElseGet(null);
	}

	public List<Odsustvo> findAll() {
		return odsustvoRepository.findAll();
	}

	@Transactional(readOnly = false)
	public Odsustvo save(Odsustvo odsustvo) {
		return odsustvoRepository.save(odsustvo);
	}

	public void remove(Long id) {
		odsustvoRepository.deleteById(id);
	}

	public List<Odsustvo> findByOdgovorenoFalseAndKlinikaIDAndPocetakAfter(long id, long sad) {
		return odsustvoRepository.findByOdgovorenoFalseAndKlinikaIdAndPocetakAfter(id, sad);
	}

	// Metoda koja vraća radni kalendar radnika
	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id) {
		List<Odsustvo> odsustva = odsustvoRepository.findByPodnosilacIdAndOdobreno(id, true);
		List<RadniKalendarDTO> kalendar = new ArrayList<>();
		for (Odsustvo odsustvo : odsustva) {
			kalendar.add(new RadniKalendarDTO(odsustvo.getPocetak(), odsustvo.getKraj(), "Godisnji odmor", 0, "", "",
					"", ""));
		}
		return kalendar;
	}

	// Metoda koja obaveštava podnosioca o zahtevu za odsustvo
	public Odsustvo updateOdsustvo(Odsustvo odsustvo) {
		Odsustvo ods = odsustvoRepository.save(odsustvo);
		return ods;
	}
	
	// Metoda koja ažurira odsustvo (prihvata ili odbija)
	@Transactional(readOnly = false)
	public Boolean azurirajOdsustvo(OdsustvoDTO odsustvoDTO) {
		Odsustvo odsustvo = findOne(odsustvoDTO.getId());
		if (odsustvo == null) {
			return false;
		}
		if(odsustvo.isOdgovoreno()) {
			return false;
		}
		odsustvo.setObrazlozenje(odsustvoDTO.getObrazlozenje());
		odsustvo.setOdgovoreno(odsustvoDTO.isOdgovoreno());
		odsustvo.setOdobreno(odsustvoDTO.isOdobreno());

		odsustvo = odsustvoRepository.save(odsustvo);
		
		return true;
		
	}

}
