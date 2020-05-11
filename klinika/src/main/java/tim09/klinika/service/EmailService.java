package tim09.klinika.service;

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
import tim09.klinika.model.Pacijent;
import tim09.klinika.model.VerifikacioniToken;

@EnableAsync
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private VerifikacioniTokenService verifikacioniTokenService;

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

}
