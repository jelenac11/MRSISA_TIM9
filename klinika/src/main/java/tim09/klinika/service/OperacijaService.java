package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.OperacijaDTO;
import tim09.klinika.dto.PregledDTO;
import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.dto.SlobodanTerminOperacijaDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.Sala;
import tim09.klinika.repository.OperacijaRepository;

@Service
public class OperacijaService {

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private SalaService salaService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private EmailService emailService;

	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}

	public Operacija save(Operacija operacija) {
		return operacijaRepository.save(operacija);
	}

	public void remove(Long id) {
		operacijaRepository.deleteById(id);
	}

	public List<RadniKalendarDTO> kreirajRadniKalendar(Long id, long time) {
		List<Operacija> operacije = operacijaRepository.findBySalaIdAndVremeAfterAndOtkazana(id, time, false);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Operacija operacija : operacije) {
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija", operacija.getPacijent().getId(), "", "", "", ""));
		}
		return kalendar;
	}

	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id, long time) {
		List<Operacija> operacije = operacijaRepository.findByLekar(id);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Operacija operacija : operacije) {
			String sala = "";
			if (operacija.getSala() != null) {
				sala = operacija.getSala().getNaziv();
			}
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija", operacija.getPacijent().getId(), operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime(), "", ispisiLekare(operacija.getLekari()), sala));
		}
		return kalendar;
	}

	public List<Operacija> findByKlinikaIdAndSalaIdAndVremeAfter(Long id, long time) {
		return operacijaRepository.findByKlinikaIdAndSalaIdIsNullAndVremeAfterAndOtkazana(id, time, false);
	}

	public void dodijeliSalu(SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		Sala sala = salaService.findOne(slobodanTerminDTO.getSala().getId());
		Operacija operacija = operacijaRepository.findById(slobodanTerminDTO.getOperacijaId()).orElseGet(null);
		operacija.setSala(sala);
		AdminKlinike ak = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		operacija.setKlinika(ak.getKlinika());
		operacija.setVreme(slobodanTerminDTO.getDatumiVreme());
		for (LekarDTO lekarDTO : slobodanTerminDTO.getLekari()) {
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			operacija.getLekari().add(lekar);
			emailService.obavestiLekaraZaOperaciju(operacija, "aleksa.goljovic4@gmail.com");
		}
		emailService.obavestiPacijentaZaOperaciju(operacija, "aleksa.goljovic4@gmail.com");
		operacijaRepository.save(operacija);
	}

	public List<Operacija> findByOtkazanaAndKlinikaIdAndVremeAfter(boolean b, Long id, long time) {
		return operacijaRepository.findByOtkazanaAndKlinikaIdAndVremeAfterAndSalaIdIsNotNull(b, id, time);
	}

	public List<OperacijaDTO> vratiOperacijePacijenta(long id) {
		List<Operacija> operacije = operacijaRepository.findByPacijentIdAndOtkazanaFalse(id);
		List<OperacijaDTO> operacijeDTO = new ArrayList<OperacijaDTO>();
		if (operacije != null) {
			for (Operacija o : operacije) {
				operacijeDTO.add(new OperacijaDTO(o));
			}
		}
		return operacijeDTO;
	}
	
	private String ispisiLekare(Set<Lekar> set) {
		StringBuilder retVal = new StringBuilder();
		for (Lekar lekar : set) {
			retVal.append(lekar.getIme() + " " + lekar.getPrezime() + ", ");
		}
		return retVal.toString().substring(0, retVal.toString().length() - 2);
	}

}
