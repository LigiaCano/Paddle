package business.api;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import business.api.exceptions.AlreadyExistConnectionException;
import business.api.exceptions.AlreadyExistUserFieldException;
import business.api.exceptions.InvalidCodeFieldException;
import business.api.exceptions.NotFoundConnectionException;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.FACEBOOK)
public class FacebookResource {

	private static final Logger log = LoggerFactory.getLogger(FacebookResource.class);

	private Facebook facebook;
	private FacebookConnectionFactory facebookConnectionFactory;
	private ConnectionRepository connectionRepository;

	@Autowired
	public FacebookResource(FacebookConnectionFactory facebookConnectionFactory,
			ConnectionRepository connectionRepository, Facebook facebook) {
		this.facebookConnectionFactory = facebookConnectionFactory;
		this.connectionRepository = connectionRepository;
		this.facebook = facebook;
	}

	@RequestMapping(value = Uris.CONNECT, method = RequestMethod.POST)
	public void connect(@RequestParam String token)
			throws RevokedAuthorizationException, ExpiredAuthorizationException, InvalidAuthorizationException, AlreadyExistConnectionException {
		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			throw new AlreadyExistConnectionException();
		}
		AccessGrant accessGrant = new AccessGrant(token);
		Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
		connectionRepository.addConnection(connection);
	}

	@RequestMapping(value = Uris.PROFILE, method = RequestMethod.GET)
	public User profile() throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		User user = facebook.userOperations().getUserProfile();
		return user;
	}

	@RequestMapping(value = Uris.FRIENDS, method = RequestMethod.GET)
	public List<User> friends() throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		List<User> friends = facebook.friendOperations().getFriendProfiles();
		return friends;
	}

	@RequestMapping(value = Uris.FEEDS, method = RequestMethod.GET)
	public List<Post> showFeed() throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		List<Post> feed = facebook.feedOperations().getFeed();
		return feed;

	}

	@RequestMapping(value = Uris.FEEDS, method = RequestMethod.POST)
	public void postUpdate(@RequestParam(required = true) String message)
			throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		facebook.feedOperations().updateStatus(message);
	}

	@RequestMapping(value = Uris.PAGES, method = RequestMethod.GET)
	public PagedList<Account> showPage() throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		PagedList<Account> page = facebook.pageOperations().getAccounts();
		return page;
	}

	@RequestMapping(value = Uris.PAGES, method = RequestMethod.POST)
	public void postPage(@RequestParam(required = true) String pageId, @RequestParam(required = true) String message)
			throws NotFoundConnectionException, InsufficientPermissionException {
		if (facebook == null){
			throw new NotFoundConnectionException();
		}
		PagePostData pagePostData = new PagePostData(pageId);
		pagePostData.message(message);
		if (facebook.pageOperations().isPageAdmin(pageId)) {
			facebook.pageOperations().post(pagePostData);
		}
	}

//	private void foundConnection() throws NotFoundConnectionException {
//		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//			throw new NotFoundConnectionException();
//		}
//	}

}
