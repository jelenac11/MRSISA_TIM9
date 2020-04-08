package tim09.klinika.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



import org.springframework.stereotype.Service;
import tim09.klinika.model.Pacijent;


@Service
public class KorisnikServis {
	//postoj jedan difoltni korisnik
	Pacijent p = new Pacijent("pacijent@gmail.com", "lozinka", "Pera", "Perić", "Staparski put 23", "Sombor", "R Srbija", "0099");
	
	public Pacijent vratiKorisnika() {
		return p;
	}
	
	public void izmeniPodatke(Pacijent _p) {
		p = _p;
	}
}
