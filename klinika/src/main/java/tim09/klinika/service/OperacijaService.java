package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.dto.SlobodanTerminOperacijaDTO;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Operacija;
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
		List<Operacija> operacije = operacijaRepository.findBySalaIdAndVremeAfter(id, time);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Operacija operacija : operacije) {
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija"));
		}
		return kalendar;
	}

	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id, long time) {
		List<Operacija> operacije = operacijaRepository.findByLekariAndVremeAfter(id, time);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Operacija operacija : operacije) {
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija"));
		}
		return kalendar;
	}

	public List<Operacija> findByKlinikaIdAndSalaIdAndVremeAfter(Long id, long time) {
		return operacijaRepository.findByKlinikaIdAndSalaIdIsNullAndVremeAfter(id, time);
	}

	public void dodijeliSalu(SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		Sala sala = salaService.findOne(slobodanTerminDTO.getSala().getId());
		Operacija operacija = operacijaRepository.findById(slobodanTerminDTO.getOperacijaId()).orElseGet(null);
		operacija.setSala(sala);
		operacija.setVreme(slobodanTerminDTO.getDatumiVreme());
		for (LekarDTO lekarDTO : slobodanTerminDTO.getLekari()) {
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			operacija.getLekari().add(lekar);
			emailService.obavestiLekaraZaOperaciju(operacija, "aleksa.goljovic4@gmail.com");
		}
		emailService.obavestiPacijentaZaOperaciju(operacija, "aleksa.goljovic4@gmail.com");
		operacijaRepository.save(operacija);
	}

}
