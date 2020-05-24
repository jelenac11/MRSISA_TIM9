package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.Sifrarnik;
import klinika.repository.SifrarnikRepository;

@Service
public class SifrarnikService {

	@Autowired
	private SifrarnikRepository sifrarnikRepository;

	public Sifrarnik findByTipSifrarnika(String tip) {
		return sifrarnikRepository.findByTipSifrarnika(tip);
	}

	public Sifrarnik findOne(Long id) {
		return sifrarnikRepository.findById(id).orElseGet(null);
	}

	public List<Sifrarnik> findAll() {
		return sifrarnikRepository.findAll();
	}

	public Sifrarnik save(Sifrarnik sifrarnik) {
		return sifrarnikRepository.save(sifrarnik);
	}

	public void remove(Long id) {
		sifrarnikRepository.deleteById(id);
	}
}
