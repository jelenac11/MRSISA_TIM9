package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Sifrarnik;
import tim09.klinika.repository.SifrarnikRepository;

@Service
public class SifrarnikService {

	@Autowired
	private SifrarnikRepository sifrarnikRepository;

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
