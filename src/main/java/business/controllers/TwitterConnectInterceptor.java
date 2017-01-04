package business.controllers;

import org.springframework.social.DuplicateStatusException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class TwitterConnectInterceptor implements ConnectInterceptor<Twitter> {
	private static final String PARAMETER = "postTweet";

	private static final String AUTORIZATIONPAGE = "twitterConnect";

	private static final String ATTRIBUTE = AUTORIZATIONPAGE + "." + PARAMETER;

	@Override
	public void preConnect(ConnectionFactory<Twitter> provider, MultiValueMap<String, String> parameters,
			WebRequest request) {
		if (StringUtils.hasText(request.getParameter(PARAMETER))) {
			request.setAttribute(ATTRIBUTE, Boolean.TRUE, WebRequest.SCOPE_SESSION);
		}
	}

	@Override
	public void postConnect(Connection<Twitter> connection, WebRequest request) {
		if (request.getAttribute(ATTRIBUTE, WebRequest.SCOPE_SESSION) != null) {
			try {
				connection.updateStatus("I've connected with Paddle MIW!");
			} catch (DuplicateStatusException e) {
			}
			request.removeAttribute(ATTRIBUTE, WebRequest.SCOPE_SESSION);
		}

	}

}
