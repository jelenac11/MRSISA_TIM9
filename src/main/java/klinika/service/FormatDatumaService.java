package klinika.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class FormatDatumaService {

	// Formatira radno vreme
	public long getRadnoVrijemeLongIzLong(long datum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(datum);

		return getMinuteULong(cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE));
	}

	// Pomoćna metoda
	public long getMinuteULong(long minuta) {
		return minuta * 60000;
	}
}
