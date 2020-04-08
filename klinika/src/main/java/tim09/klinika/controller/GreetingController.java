package tim09.klinika.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.service.GreetingService;

//vraca http odgovor cije telo je poruka hello world
@RestController
public class GreetingController {

	@Autowired
	private GreetingService service;
	
	@RequestMapping("/api/greetings")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAssets(){
		String msg = service.getGreeting().getMessage();
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
