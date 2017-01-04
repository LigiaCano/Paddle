package business.controllers;

import org.springframework.social.ApiException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class FacebookConnectInterceptor implements ConnectInterceptor<Facebook> {
	private static final String PARAMETER = "postToWall";

	private static final String AUTORIZATIONPAGE = "facebookConnect";

	private static final String ATTRIBUTE = AUTORIZATIONPAGE + "." + PARAMETER;

	@Override
	public void preConnect(ConnectionFactory<Facebook> provider, MultiValueMap<String, String> parameters,
			WebRequest request) {
		if (StringUtils.hasText(request.getParameter(PARAMETER))) {
			request.setAttribute(ATTRIBUTE, Boolean.TRUE, WebRequest.SCOPE_SESSION);
		}
	}

	@Override
	public void postConnect(Connection<Facebook> connection, WebRequest request) {
		if (request.getAttribute(ATTRIBUTE, WebRequest.SCOPE_SESSION) != null) {
			try {
				connection.updateStatus("I've connected with Paddle MIW!");
			} catch (ApiException e) {
			}
			request.removeAttribute(ATTRIBUTE, WebRequest.SCOPE_SESSION);
		}
	}

}
