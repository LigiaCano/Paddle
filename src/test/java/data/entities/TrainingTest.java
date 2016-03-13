package data.entities;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import data.entities.User;
import data.entities.Training;

public class TrainingTest {

	@Test
	public void testaddPlayer() {
		User trainer = new User("trainer", "trainer@gmail.com", "t", Calendar.getInstance());
		Court court = new Court(1);
		Calendar starDate = Calendar.getInstance();
		starDate.add(Calendar.DAY_OF_YEAR, 2);
		starDate.set(Calendar.HOUR_OF_DAY, 10);
		starDate.set(Calendar.MINUTE, 0);
		starDate.set(Calendar.SECOND, 0);
		starDate.set(Calendar.MILLISECOND, 0);

		Calendar endDate = (Calendar) starDate.clone();
		endDate.add(Calendar.WEEK_OF_YEAR, 2);
		endDate.add(Calendar.HOUR_OF_DAY, 1);
		
		Training training = new Training(trainer, court, starDate, endDate);
		training.addPlayer(new User("u", "u@gmail.com", "p", Calendar.getInstance()));
		System.out.println(training.toString());
		assertEquals(training.getPlayers().size(),1);
		
	}

}
