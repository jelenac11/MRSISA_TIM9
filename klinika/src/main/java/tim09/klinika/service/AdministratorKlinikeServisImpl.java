package tim09.klinika.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.model.Sala;
import tim09.klinika.repository.InMemorySalaRepository;
import tim09.klinika.repository.LekarRepository;
import tim09.klinika.repository.OdsustvoRepository;

@Service
public class AdministratorKlinikeServisImpl implements AdministratorKlinikeServis {

	private static String potvrdaZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je prihvacen.";
	private static String odbijanjeZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je odbijen. Razlog odbijanja je sljedeci: \n";
	
	@Autowired
	private OdsustvoRepository odsustvoRepository;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private InMemorySalaRepository salaRepository;

	@Autowired
	private LekarRepository lekarRepository;

	SimpleDateFormat sdf = new SimpleDateFormat();

	
	@Override
	public Collection<Lekar> vratiSveLekare() {
		Collection<Lekar> lekari = lekarRepository.findAll();
		return lekari;
	}

	@Override
	public Collection<Sala> vratiSveSale() {
		Collection<Sala> sale = salaRepository.vratiSveSale();
		return sale;
	}

	@Override
	public boolean dodajSalu(Sala sala) {
		boolean uspesno = salaRepository.dodajSalu(sala);
		return uspesno;
	}

	@Override
	public boolean dodajLekara(Lekar lekar) {
		//boolean uspesno = lekarRepository.dodajLekara(lekar);
		//return uspesno;
		return true;
	}

	@Override
	public List<Odsustvo> vratiZahtjeveNaCekanju() {
		return odsustvoRepository.findByOdgovoreno(false);
	}

	@Override
	public Odsustvo updateZahtjeveNaCekanju(Odsustvo zahtjev) {
		Odsustvo odsustvo = odsustvoRepository.save(zahtjev);
		
		try {
			emailService.posaljiEmail(zahtjev.getPodnosilac().getEmail(), "Odgovor na zahtjev za odsustvom",
					zahtjev.isOdobreno() ? potvrdaZahtjeva : odbijanjeZahtjeva + zahtjev.getObrazlozenje());
		} catch (Exception e) {

		}
		
		return odsustvo;
	}
}
