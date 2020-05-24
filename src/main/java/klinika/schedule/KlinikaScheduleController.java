package klinika.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import klinika.dto.PretragaLekaraDTO;
import klinika.model.Lekar;
import klinika.model.Operacija;
import klinika.model.Pregled;
import klinika.model.Sala;
import klinika.service.EmailService;
import klinika.service.LekarService;
import klinika.service.OperacijaService;
import klinika.service.PregledService;
import klinika.service.SalaService;

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
				List<Sala> sale = salaService.findByIdKlinikaAndVreme(p.getKlinika().getId(), p.getVreme());
				if (sale != null && !salePrazne(sale)) {
					p.setSala(sale.get(0));
					pregledService.save(p);
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
							List<Sala> sale2 = salaService.findByIdKlinikaAndVreme(p.getKlinika().getId(), ter);
							if (sale2 != null && !salePrazne(sale2)) {
								p.setSala(sale2.get(0));
								p.setVreme(ter);
								pregledService.save(p);
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
				List<Sala> sale = salaService.findByIdKlinikaAndVreme(o.getKlinika().getId(), o.getVreme());
				if (sale != null && !salePrazne(sale)) {
					o.setSala(sale.get(0));
					operacijaService.save(o);
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
							List<Sala> sale2 = salaService.findByIdKlinikaAndVreme(o.getKlinika().getId(), ter);
							if (sale2 != null && salePrazne(sale2)) {
								o.setSala(sale2.get(0));
								o.setVreme(ter);
								Lekar prvi = o.getLekari().iterator().next();
								o.getLekari().clear();
								o.getLekari().add(prvi);
								operacijaService.save(o);
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

	private boolean salePrazne(List<Sala> sale) {
		return sale.isEmpty();
	}
}
