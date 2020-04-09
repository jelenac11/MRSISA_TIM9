package tim09.klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class EmailServiceImpl {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void posaljiEmail(String to, String subject, String text) throws MailException, InterruptedException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(env.getProperty("spring.mail.username"));
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);

	}

}
