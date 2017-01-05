package api;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.api.Uris;
import business.wrapper.AvailableTime;

public class ReserveResourceFunctionalTesting {

	RestService restService = new RestService();

//	@Before
//	public void connect() {
//		String token = restService.registerAndLoginPlayer();
//		
////		new RestBuilder<Object>(RestService.URL).path("/twitter/connect")
////				.param("token", "200635849-5JmaThmdggWk2aU9inGS2ceL3g4ywFJdRWS44hgN")
////				.param("secret", "PPJLqMKIouXNqHImApKjgkjqyjoNrArhByyov3T4S8mxb").basicAuth(token, "").post().build();
//	}

	@Test
	public void testshowAvailability() {
		restService.createCourt("1");
		restService.createCourt("2");
		String token = restService.registerAndLoginPlayer();
		
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DAY_OF_YEAR, 1);
		day.set(Calendar.HOUR_OF_DAY, 12);
		new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "")
				.body(new AvailableTime(1, day)).post().build();
		day.set(Calendar.HOUR_OF_DAY, 14);
		new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "")
				.body(new AvailableTime(2, day)).post().build();
		String day2 = "" + day.getTimeInMillis();
		String response = new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).path(Uris.AVAILABILITY)
				.basicAuth(token, "").param("day", day2).clazz(String.class).get().build();
		LogManager.getLogger(this.getClass()).info("testshowAvailability (" + response + ")");
	}

	@Test
	public void testReserveCourt() {
		restService.createCourt("1");
		restService.createCourt("2");
		String token = restService.registerAndLoginPlayer();
		new RestBuilder<Object>(RestService.URL).path("/facebook/connect")
		.param("token",
				"EAAXUt5rij5gBAOdZB3TSGt6lhXl4fldwttZCBEQKkSER4wzoSNuhfWFBmGLPeBFoao3aUXknJ4NncirvmkblZCO7x1VqKVkS5ZA4C552izBZB7ZAe0eZBzOlClfTZBFcSvSso1lyQXrs09Tv3lp3pGZB5msCrFFzUfl7uM2WMFrK7IUdSDzH6hUFb")
		.basicAuth(token, "").post().build();
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DAY_OF_YEAR, 1);
		day.set(Calendar.HOUR_OF_DAY, 12);
		new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "")
				.body(new AvailableTime(1, day)).post().build();
		day.set(Calendar.HOUR_OF_DAY, 14);
		new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "")
				.body(new AvailableTime(2, day)).post().build();
	}

	@After
	public void deleteAll() {
		new RestService().deleteAll();
	}

}
