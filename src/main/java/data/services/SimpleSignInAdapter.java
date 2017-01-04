package data.services;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SimpleSignInAdapter implements SignInAdapter{

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		SignInUtils.signin(userId);	
		return null;
	}

}
