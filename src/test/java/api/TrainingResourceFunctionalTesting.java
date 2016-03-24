package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.CreateTraining;

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
		String token = restService.loginTrainer();
		Calendar starDate = Calendar.getInstance();
		starDate.set(Calendar.HOUR_OF_DAY, 10);
		new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).body(new CreateTraining(starDate, 1, 1)).basicAuth(token, "").post()
				.build();
	}
	
	@After
	public void deleteAll() {
		new RestService().deleteAll();
	}

}
