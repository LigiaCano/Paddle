package business.controllers;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.wrapper.CreateTraining;
import config.PersistenceConfig;
import config.TestsPersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, TestsPersistenceConfig.class })
public class TrainingControllerTest {
	 
	@Autowired
	private TrainingController trainingController;

	@Test
	public void testCreateTraining() {
		CreateTraining createTraining = new CreateTraining(Calendar.getInstance(), 1, 3);
		Calendar starDate = createTraining.getStartDate();
		starDate.add(Calendar.DAY_OF_YEAR, 2);
		starDate.set(Calendar.HOUR_OF_DAY, 10);
		starDate.set(Calendar.MINUTE, 0);
		starDate.set(Calendar.SECOND, 0);
		starDate.set(Calendar.MILLISECOND, 0);
		Calendar endDate = (Calendar) starDate.clone();
		endDate.add(Calendar.WEEK_OF_YEAR, createTraining.getNumberWeek() - 1);
		endDate.add(Calendar.HOUR_OF_DAY, 1);
		createTraining.setTrainerUsername("trainer");
		createTraining.setEndDate(endDate);
		assertFalse(trainingController.createTraining(createTraining));
	}
	
	@Test
	public void testShowTrainings() {
		Calendar date = Calendar.getInstance();
		assertEquals(trainingController.showTrainings(date).size(), 1);
		System.out.println(trainingController.showTrainings(date));
	}

	@Test
	public void testRegisterTraining() {
		assertFalse(trainingController.registerTraining("u4", 1));
	}

	@Test
	public void testPlayerExist() {
		assertTrue(trainingController.playerExist(1, "u3"));
	}

	@Test
	public void testDeleteTrainingPlayer() {
		assertTrue(trainingController.deleteTrainingPlayer("u3", 1));
		assertTrue(trainingController.registerTraining("u3", 1));
	}

}
