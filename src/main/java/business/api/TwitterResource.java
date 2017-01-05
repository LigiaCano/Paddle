package business.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.User;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistConnectionException;
import business.api.exceptions.InvalidCodeFieldException;
import business.api.exceptions.NotFoundConnectionException;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TWITTER)
public class TwitterResource {
	private static final Logger log = LoggerFactory.getLogger(FacebookResource.class);

	private Twitter twitter;
	private TwitterConnectionFactory twitterConnectionFactory;
	private ConnectionRepository connectionRepository;

	@Autowired
	public TwitterResource(TwitterConnectionFactory twitterConnectionFactory, ConnectionRepository connectionRepository,
			Twitter twitter) {
		this.twitterConnectionFactory = twitterConnectionFactory;
		this.connectionRepository = connectionRepository;
		this.twitter = twitter;
	}

	private OAuthToken requestToken;

	@RequestMapping(value = Uris.CONNECT, method = RequestMethod.POST)
	public void connect(@RequestParam String token, @RequestParam String secret)
			throws InvalidAuthorizationException, AlreadyExistConnectionException {
		if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
			throw new AlreadyExistConnectionException();
		}
		OAuthToken accessToken = new OAuthToken(token, secret);
		Connection<Twitter> connection = twitterConnectionFactory.createConnection(accessToken);
		connectionRepository.addConnection(connection);
	}

	@RequestMapping(value = Uris.PROFILE, method = RequestMethod.GET)
	public TwitterProfile profile() throws NotFoundConnectionException {
		foundConnection();
		TwitterProfile user = twitter.userOperations().getUserProfile();
		return user;
	}
	

	@RequestMapping(value = Uris.TIMELINETYPE + Uris.TIMELINE, method = RequestMethod.GET)
	public List<Tweet> showTimeline(@PathVariable("timelineType") String timelineType)
			throws NotFoundConnectionException {
		foundConnection();
		List<Tweet> tweet = null;
		if (timelineType.equals("Home")) {
			tweet = twitter.timelineOperations().getHomeTimeline();
		}
		if (timelineType.equals("User")) {
			tweet = twitter.timelineOperations().getUserTimeline();
		}
		if (timelineType.equals("Mentions")) {
			tweet = twitter.timelineOperations().getMentions();
		}
		if (timelineType.equals("Favorites")) {
			tweet = twitter.timelineOperations().getFavorites();
		}
		return tweet;
	}

	@RequestMapping(value = Uris.TWEETS, method = RequestMethod.POST)
	public void postTweet(@RequestParam(required = true) String message) throws NotFoundConnectionException {
		foundConnection();
		twitter.timelineOperations().updateStatus(message);
	}
	
	@RequestMapping(value = Uris.RETWEETS, method = RequestMethod.POST)
	public void postReTweet(@RequestParam(required = true) long tweetId) throws NotFoundConnectionException {
		foundConnection();
		twitter.timelineOperations().retweet(tweetId);
	}
	
	@RequestMapping(value = Uris.SEARCH, method = RequestMethod.GET)
	public SearchResults search(@RequestParam(required = true) String search)throws NotFoundConnectionException{
		SearchResults results = twitter.searchOperations().search(search);
		return results;
	}

	private void foundConnection() throws NotFoundConnectionException {
		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			throw new NotFoundConnectionException();
		}
	}

}
