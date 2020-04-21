package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Odsustvo;
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
	
	public List<Odsustvo> findByOdgovoreno(boolean odgovoreno){
		return odsustvoRepository.findByOdgovoreno(odgovoreno);
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
