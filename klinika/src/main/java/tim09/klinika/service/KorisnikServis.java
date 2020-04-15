package tim09.klinika.service;


import org.springframework.stereotype.Service;
import tim09.klinika.model.Pacijent;


@Service
public class KorisnikServis {
	//postoj jedan difoltni korisnik
	Pacijent p;
	public Pacijent vratiKorisnika() {
		return p;
	}
	
	public void izmeniPodatke(Pacijent _p) {
		p = _p;
	}
}
