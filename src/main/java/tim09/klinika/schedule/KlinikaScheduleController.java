package tim09.klinika.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.Sala;
import tim09.klinika.service.EmailService;
import tim09.klinika.service.LekarService;
import tim09.klinika.service.OperacijaService;
import tim09.klinika.service.PregledService;
import tim09.klinika.service.SalaService;

@Controller
public class KlinikaScheduleController {

	@Autowired
	private PregledService pregledService;

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private SalaService salaService;

	@Scheduled(cron = "${klinika.cron}")
	public void proveriPregledeIOperacijeBezSale() {
		List<Pregled> pregledi = pregledService.dobaviSvePregledeBezSale();
		List<Operacija> operacije = operacijaService.dobaviSveOperacijeBezSale();

		if (pregledi != null) {
			for (Pregled p : pregledi) {
				List<Sala> sale = salaService.findByIdKlinikaAndVreme(p.getKlinika().getId(), p.getVreme(), 3600000);
				if (sale != null) {
					if (!sale.isEmpty()) {
						p.setSala(sale.get(0));
						pregledService.save(p);
					}
				}

				long dani = 0;
				while (p.getSala() == null) {
					long dan = (p.getVreme() / 86400000) + dani * 86400000;
					long ostatak = dan % 8640000;
					dan -= ostatak;
					PretragaLekaraDTO pld = new PretragaLekaraDTO(p.getLekar().getId(), dan);
					List<Long> termini = lekarService.vratiSlobodneTermine(pld);
					if (termini != null) {
						for (Long ter : termini) {
							List<Sala> sale2 = salaService.findByIdKlinikaAndVreme(p.getKlinika().getId(), ter, 3600000);
							if (sale2 != null) {
								if (!sale2.isEmpty()) {
									p.setSala(sale2.get(0));
									p.setVreme(ter);
									pregledService.save(p);
								}
							}
						}
					}
					dani++;
				}
				emailService.posaljiLinkPotvrdePregleda(p, "aleksa.goljovic4@gmail.com");
			}
		}
		
		if (operacije != null) {
			for (Operacija o : operacije) {
				List<Sala> sale = salaService.findByIdKlinikaAndVreme(o.getKlinika().getId(), o.getVreme(), 3600000);
				if (sale != null) {
					if (!sale.isEmpty()) {
						o.setSala(sale.get(0));
						operacijaService.save(o);
					}
				}

				long dani = 0;
				while (o.getSala() == null) {
					long dan = (o.getVreme() / 86400000) + dani * 86400000;
					long ostatak = dan % 8640000;
					dan -= ostatak;
					PretragaLekaraDTO pld = new PretragaLekaraDTO(o.getLekari().iterator().next().getId(), dan);
					List<Long> termini = lekarService.vratiSlobodneTermine(pld);
					if (termini != null) {
						for (Long ter : termini) {
							List<Sala> sale2 = salaService.findByIdKlinikaAndVreme(o.getKlinika().getId(), ter, 3600000);
							if (sale2 != null) {
								if (!sale2.isEmpty()) {
									o.setSala(sale2.get(0));
									o.setVreme(ter);
									Lekar prvi = o.getLekari().iterator().next();
									o.getLekari().clear();
									o.getLekari().add(prvi);
									operacijaService.save(o);
								}
							}
						}
					}
					dani++;
				}
				for (Lekar lekar: o.getLekari()) {
					emailService.obavestiLekaraZaOperaciju(o, "aleksa.goljovic4@gmail.com");
				}
				emailService.obavestiPacijentaZaOperaciju(o, "aleksa.goljovic4@gmail.com");
			}
		}
	}
}
