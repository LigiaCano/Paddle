package business.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;

@Controller
public class SocialController {

	private ConnectionRepository connectionRepository;

	@Autowired
	public SocialController(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	public void postFacebook(String message) throws InsufficientPermissionException {
		Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
		if (connection != null) {
			connection.getApi().feedOperations().updateStatus(message);
		}

	}

	public void postTwitter(String message) throws InsufficientPermissionException {
		Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
		if (connection != null) {
			connection.getApi().timelineOperations().updateStatus(message);
		}

	}

}
