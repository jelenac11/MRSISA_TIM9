package tim09.klinika.service;



import org.springframework.stereotype.Service;
import tim09.klinika.model.Pacijent;


@Service
public class KorisnikServis {
	//postoj jedan difoltni korisnik
	Pacijent p = new Pacijent("pacijent@gmail.com", "lozinka", "Pera", "Perić", "Staparski put 23", "Sombor", "R Srbija", "0099");
	
	public Pacijent vratiKorisnika() {
		return p;
	}
}
