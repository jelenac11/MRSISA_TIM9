package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.AdminKlinike;
import klinika.repository.AdminKlinikeRepository;

@Service
public class AdminKlinikeService {

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;

	public AdminKlinike findByEmail(String email) {
		return adminKlinikeRepository.findByEmail(email);
	}

	public AdminKlinike findOne(Long id) {
		return adminKlinikeRepository.findById(id).orElseGet(null);
	}

	public List<AdminKlinike> findAll() {
		return adminKlinikeRepository.findAll();
	}

	public AdminKlinike save(AdminKlinike adminKlinike) {
		return adminKlinikeRepository.save(adminKlinike);
	}

	public void remove(Long id) {
		adminKlinikeRepository.deleteById(id);
	}
}
