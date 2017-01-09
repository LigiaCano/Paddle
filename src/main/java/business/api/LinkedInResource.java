package business.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.User;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistConnectionException;
import business.api.exceptions.InvalidCodeFieldException;
import business.api.exceptions.NotFoundConnectionException;
import business.wrapper.UserWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.LINKEDIN)
public class LinkedInResource {

	private LinkedIn linkedIn;
	private LinkedInConnectionFactory linkedInConnectionFactory;
	private ConnectionRepository connectionRepository;

	@Autowired
	public LinkedInResource(LinkedInConnectionFactory linkedInConnectionFactory,
			ConnectionRepository connectionRepository, LinkedIn linkedIn) {
		this.linkedInConnectionFactory = linkedInConnectionFactory;
		this.connectionRepository = connectionRepository;
		this.linkedIn = linkedIn;
	}

	@RequestMapping(value = Uris.CONNECT, method = RequestMethod.POST)
	public void connect(@RequestParam String token)
			throws ExpiredAuthorizationException, InvalidAuthorizationException, AlreadyExistConnectionException {
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) != null) {
			throw new AlreadyExistConnectionException();
		}
		AccessGrant accessGrant = new AccessGrant(token);
		Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(accessGrant);
		connectionRepository.addConnection(connection);
	}

	@RequestMapping(value = Uris.PROFILE, method = RequestMethod.GET)
	public LinkedInProfile profile() throws NotFoundConnectionException {
		if (linkedIn == null){
			throw new NotFoundConnectionException();
		}
		LinkedInProfile user = linkedIn.profileOperations().getUserProfile();
		return user;
	}

	@RequestMapping(value = Uris.CONNECTIONS, method = RequestMethod.GET)
	public List<LinkedInProfile> connections() throws NotFoundConnectionException {
		if (linkedIn == null){
			throw new NotFoundConnectionException();
		}
		List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
		return connections;
	}

//	private void foundConnection() throws NotFoundConnectionException {
//		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
//			throw new NotFoundConnectionException();
//		}
//	}

}
