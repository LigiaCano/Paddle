package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.CreateTraining;
import business.wrapper.TrainingWrapper;

public class TrainingResourceFunctionalTesting {

	RestService restService = new RestService();

	@Test
	public void testCreateTrainingForbidden() {
		try {
			restService.createCourt("1");
			String token = restService.registerAndLoginPlayer();
			CreateTraining createTraining = new CreateTraining(Calendar.getInstance(), 1, 1);
			new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).body(createTraining).basicAuth(token, "")
					.post().build();
			fail();
		} catch (HttpClientErrorException httpError) {
			System.out.println(httpError.getStatusCode());
			assertEquals(HttpStatus.FORBIDDEN, httpError.getStatusCode());
			LogManager.getLogger(this.getClass()).info(
					"testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
		}
	}

	@Test
	public void testCreateTraining() {
		restService.createCourt("1");
		Calendar starDate = Calendar.getInstance();
		starDate.set(Calendar.HOUR_OF_DAY, 10);
		CreateTraining createTraining = new CreateTraining(starDate, 1, 1);
		restService.createTraining(createTraining);
	}
	
	@Test
	public void testShowTrainings(){
		restService.createCourt("2");
		Calendar starDate = Calendar.getInstance();
		starDate.add(Calendar.DAY_OF_YEAR, 1);
		starDate.set(Calendar.HOUR_OF_DAY, 10);
		CreateTraining createTraining = new CreateTraining(starDate, 1, 2);
		restService.createTraining(createTraining);
		String token = restService.registerAndLoginPlayer();
		List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).param("day","" + Calendar.getInstance().getTimeInMillis()).basicAuth(token, "")
				.clazz(TrainingWrapper[].class).get().build());
		System.out.println(list);
		assertEquals(1, list.size());
	}
	
	@After
	public void deleteAll() {
		new RestService().deleteAll();
	}

}
