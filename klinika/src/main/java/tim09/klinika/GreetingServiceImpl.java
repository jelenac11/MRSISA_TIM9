package tim09.klinika;

import org.springframework.stereotype.Service;

//osnovni servis, vraca se poruka hello world
@Service
public class GreetingServiceImpl implements GreetingService {

	@Override
	public Greeting getGreeting() {
		Greeting gt = new Greeting(2L, "Hello world!");
		return gt;
	}

}
