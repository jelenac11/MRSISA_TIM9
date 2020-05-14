package tim09.klinika.service;

import java.util.Date;
import java.util.UUID;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.PredefinisaniDTO;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pacijent;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TokenPotvrdeOperacije;
import tim09.klinika.model.TokenPotvrdePregleda;
import tim09.klinika.model.VerifikacioniToken;

@EnableAsync
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private VerifikacioniTokenService verifikacioniTokenService;

	@Autowired
	private TokenPotvrdePregledaService tokenPotvrdePregledaService;

	@Autowired
	private TokenPotvrdeOperacijeService tokenPotvrdeOperacijeService;

	@Async
	public void posaljiEmail(String to, String subject, String text) throws MailException, InterruptedException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(Environment.getProperties().getProperty("spring.mail.username"));
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

	@Async
	public void posaljiAktivacioniLink(Pacijent pt) throws MailException, InterruptedException {
		String token = UUID.randomUUID().toString();
		VerifikacioniToken vtoken = new VerifikacioniToken();
		vtoken.setId(null);
		vtoken.setToken(token);
		vtoken.setPacijent(pt);
		verifikacioniTokenService.saveToken(vtoken);
		String prima = pt.getEmail();
		String subject = "Potvrda registracije";
		String confirmationUrl = "/auth/potvrdaRegistracije/" + token;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(prima);
		email.setSubject(subject);
		email.setText("Poštovani, da biste se uspešno registrovali, kliknite na sledeći link " + "\r\n"
				+ "http://localhost:8081" + confirmationUrl);
		javaMailSender.send(email);
	}

	@Async
	public void posaljiLinkPotvrdePregleda(Pregled pregled, String prima) {
		String token = UUID.randomUUID().toString();
		TokenPotvrdePregleda tokenpp = new TokenPotvrdePregleda();
		tokenpp.setId(null);
		tokenpp.setToken(token);
		tokenpp.setPregled(pregled);
		tokenPotvrdePregledaService.saveToken(tokenpp);
		String subject = "Potvrda termina pregleda";
		String confirmationUrl = "/auth/potvrdaTerminaPregleda/" + token;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(prima);
		email.setSubject(subject);
		email.setText(
				"Poštovani, molimo Vas da potvrdite ili odbijete dodeljeni termin za pregled na sledećem linku "
						+ "\r\n" + "http://localhost:8081" + confirmationUrl);
		javaMailSender.send(email);
	}

	@Async
	public void obavestiPacijentaZaOperaciju(Operacija operacija, String prima) {
		String subject = "Zakazana operacija";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(prima);
		email.setSubject(subject);
		email.setText("Poštovani, obaveštavamo vas da imate zakazanu operaciju datuma: "
				+ new Date(operacija.getVreme()).toString() + ", u klinici " + operacija.getKlinika().getNaziv()
				+ " koja se nalazi na lokaciji " + operacija.getKlinika().getLokacija() + ".");
		javaMailSender.send(email);
	}
	
	@Async
	public void obavestiLekaraZaOperaciju(Operacija operacija, String prima) {
		String subject = "Zakazana operacija";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(prima);
		email.setSubject(subject);
		email.setText("Poštovani, obaveštavamo vas da imate zakazanu operaciju datuma: "
				+ new Date(operacija.getVreme()).toString() + ", u klinici " + operacija.getKlinika().getNaziv()
				+ " koja se nalazi na lokaciji " + operacija.getKlinika().getLokacija() + ". Operaciji prisustvuje " + operacija.getLekari().size() + " lekara.");
		javaMailSender.send(email);
	}

	@Async
	public void obavijestiLekara(Pregled pregled, String mail) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(mail);
		email.setSubject("Obavestenje o novom zakazanom pregledu");
		email.setText("Postovani, obavestavamo Vas, da " + new Date(pregled.getVreme()) + " imate zakazan pregled.");
		javaMailSender.send(email);
	}

}
