package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.model.Operacija;
import tim09.klinika.repository.OdsustvoRepository;

@Service
public class OdsustvoService {

	private static String potvrdaZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je prihvacen.";
	private static String odbijanjeZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je odbijen. Razlog odbijanja je sljedeci: \n";
	private static String naslov="Odgovor na zahtjev za odsustvom";
	
	@Autowired
	private OdsustvoRepository odsustvoRepository;
	
	@Autowired
	EmailService mailService;

	public Odsustvo findOne(Long id) {
		return odsustvoRepository.findById(id).orElseGet(null);
	}

	public List<Odsustvo> findAll() {
		return odsustvoRepository.findAll();
	}

	public Odsustvo save(Odsustvo odsustvo) {
		return odsustvoRepository.save(odsustvo);
	}

	public void remove(Long id) {
		odsustvoRepository.deleteById(id);
	}
	
	public List<Odsustvo> findByOdgovorenoFalseAndKlinikaID(long id){
		return odsustvoRepository.findByOdgovorenoFalseAndKlinikaId(id);
	}

	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id, long time) {
		List<Odsustvo> odsustva = odsustvoRepository.findByPodnosilacAndKrajAfter(id, time);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Odsustvo odsustvo : odsustva) {
			kalendar.add(new RadniKalendarDTO(odsustvo.getPocetak(), odsustvo.getKraj(), "Godisnji odmor"));
		}
		return kalendar;
	}
	
	public Odsustvo updateOdsustvo(Odsustvo odsustvo) {
		Odsustvo ods= odsustvoRepository.save(odsustvo);
		try {
			mailService.posaljiEmail(odsustvo.getPodnosilac().getEmail(), naslov,
					odsustvo.isOdobreno() ? potvrdaZahtjeva : odbijanjeZahtjeva + odsustvo.getObrazlozenje());
		} catch (Exception e) {
		}
		return ods;
	}

	
}
