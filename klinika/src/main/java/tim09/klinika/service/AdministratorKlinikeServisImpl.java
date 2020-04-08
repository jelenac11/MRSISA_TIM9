package tim09.klinika.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.MedicinskoOsoblje;
import tim09.klinika.model.Sala;
import tim09.klinika.model.ZahtjevZaOdsustvom;
import tim09.klinika.repository.InMemoryLekarRepository;
import tim09.klinika.repository.InMemorySalaRepository;

@Service
public class AdministratorKlinikeServisImpl implements AdministratorKlinikeServis {

	@Autowired
	private InMemorySalaRepository salaRepository;
	
	@Autowired
	private InMemoryLekarRepository lekarRepository;

	@Autowired
	private JavaMailSender javaMailSender;
	SimpleDateFormat sdf = new SimpleDateFormat();

	ArrayList<ZahtjevZaOdsustvom> zahtjevi = new ArrayList<ZahtjevZaOdsustvom>(Arrays.asList(
			new ZahtjevZaOdsustvom(
					new MedicinskoOsoblje("milan_marinkovic98@hotmail.com", "lozinka123", "Pero", "Peric",
							"Nindza kornjaca", "Novi Sad", "Srbija"),
					new Date().getTime(), new Date().getTime(), false, false, null),
			new ZahtjevZaOdsustvom(
					new MedicinskoOsoblje("milan_marinkovic98@hotmail.com", "lozinka123", "Janko", "Jankovic",
							"Bulevar oslobodjenja", "Novi Sad", "Srbija"),
					new Date().getTime(), new Date().getTime(), false, false, null)));

	private static String potvrdaZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je prihvacen.";
	private static String odbijanjeZahtjeva = "Postovani/a, \n Vas zahtev za odsustvom je odbijen. Razlog odbijanja je sljedeci: \n";

	@Override
	public Collection<Lekar> vratiSveLekare() {
		Collection<Lekar> lekari = lekarRepository.vratiSveLekare();
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

	public ArrayList<ZahtjevZaOdsustvom> vratiZahtjeve() {
		return zahtjevi;
	}

	public ArrayList<ZahtjevZaOdsustvom> vratiZahtjeveNaCekanju() {
		ArrayList<ZahtjevZaOdsustvom> zahtjeviNacekanju = new ArrayList<ZahtjevZaOdsustvom>();
		for (ZahtjevZaOdsustvom zahtjev : zahtjevi) {
			if (!zahtjev.isStatus()) {
				zahtjeviNacekanju.add(zahtjev);
			}
		}
		return zahtjeviNacekanju;
	}

	public void updateZahtjeveNaCekanju(ZahtjevZaOdsustvom zahtjev) {
		for (ZahtjevZaOdsustvom zahtev : zahtjevi) {
			if (zahtev.equals(zahtjev)) {
				zahtev.setStanje(zahtjev.isStanje());
				zahtev.setStatus(zahtjev.isStatus());
			}
		}
		posaljiMejlOdgovorNaZahtjevZaOdustvom(zahtjev);
	}

	private void posaljiMejlOdgovorNaZahtjevZaOdustvom(ZahtjevZaOdsustvom zahtjev) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(zahtjev.getOsoba().getEmail());
		message.setSubject("Odgovor na zahtjev za odsustvom");
		if (zahtjev.isStanje()) {
			message.setText(potvrdaZahtjeva);
		} else {
			message.setText(odbijanjeZahtjeva + zahtjev.getObrazlozenje());
		}
		javaMailSender.send(message);
	}
}
