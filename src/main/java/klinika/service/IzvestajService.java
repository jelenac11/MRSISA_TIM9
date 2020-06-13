package klinika.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.dto.IzvestajDTO;
import klinika.dto.ReceptDTO;
import klinika.model.Izvestaj;
import klinika.model.Pregled;
import klinika.model.Recept;
import klinika.model.StavkaSifrarnika;
import klinika.repository.IzvestajRepository;

@Service
public class IzvestajService {

	@Autowired
	private IzvestajRepository izvestajRepository;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private StavkaSifrarnikaService stavkaSifrarnikaService;

	public Izvestaj findOne(Long id) {
		return izvestajRepository.findById(id).orElseGet(null);
	}

	public List<Izvestaj> findAll() {
		return izvestajRepository.findAll();
	}

	public Izvestaj save(Izvestaj izvestaj) {
		return izvestajRepository.save(izvestaj);
	}

	public void remove(Long id) {
		izvestajRepository.deleteById(id);
	}

	// Dodaje novi izveštaj u bazu podataka
	public Boolean dodajIzvestaj(IzvestajDTO izvestajDTO) {
		Pregled p = pregledService.findOne(izvestajDTO.getPregled().getId());
		Izvestaj i = new Izvestaj();
		i.setOpis(izvestajDTO.getOpis());
		Set<Recept> recepti = new HashSet<>();
		i.setPregled(p);
		izvestajRepository.save(i);
		for (ReceptDTO r : izvestajDTO.getRecepti()) {
			Recept rec = new Recept();
			StavkaSifrarnika lek = stavkaSifrarnikaService.findBySifra(r.getLek().getSifra());
			rec.setLek(lek);
			rec.setIzvestaj(i);
			recepti.add(rec);
			receptService.save(rec);
		}
		i.setRecepti(recepti);
		if (izvestajDTO.getDijagnoza() != null) {
			StavkaSifrarnika dijagnoza = stavkaSifrarnikaService.findBySifra(izvestajDTO.getDijagnoza().getSifra());
			i.setDijagnoza(dijagnoza);
		}
		i.setZdravstveniKarton(p.getPacijent().getKarton());
		p.getPacijent().getKarton().getBolesti().add(i);
		p.setIzvestaj(i);
		izvestajRepository.save(i);
		pregledService.save(p);
		return true;
	}

	// Menja već postojeći izveštaj
	public Boolean izmeniIzvestaj(IzvestajDTO izvestajDTO) {
		Optional<Izvestaj> i = izvestajRepository.findById(izvestajDTO.getId());
		if (i.isPresent()) {
			i.get().setOpis(izvestajDTO.getOpis());
			Set<Recept> recepti = new HashSet<>();
			for (Recept recept : i.get().getRecepti()) {
				receptService.remove(recept.getId());
			}
			for (ReceptDTO r : izvestajDTO.getRecepti()) {
				Recept rec = new Recept();
				StavkaSifrarnika lek = stavkaSifrarnikaService.findBySifra(r.getLek().getSifra());
				rec.setLek(lek);
				rec.setIzvestaj(i.get());
				recepti.add(rec);
				receptService.save(rec);
			}
			i.get().setRecepti(recepti);
			if (izvestajDTO.getDijagnoza() != null) {
				StavkaSifrarnika dijagnoza = stavkaSifrarnikaService.findBySifra(izvestajDTO.getDijagnoza().getSifra());
				i.get().setDijagnoza(dijagnoza);
			}
			izvestajRepository.save(i.get());
		}
		return true;
	}
}
