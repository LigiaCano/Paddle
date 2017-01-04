package web.presenter;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import business.controllers.UserController;
import business.wrapper.UserWrapper;
import data.services.SignInUtils;


@Controller
public class SignupPresenter {
	private final UserController userController;

	private final ProviderSignInUtils providerSignInUtils;

	@Autowired
	public SignupPresenter(UserController userController, ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository connectionRepository) {
		this.userController = userController;
		this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
	}
	
	@RequestMapping(value=Uris.SIGNUP, method=RequestMethod.GET)
	public UserWrapper signupForm(WebRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
		if (connection != null) {
			return fromProviderUser(connection.fetchUserProfile());
		} else {
			return new UserWrapper();
		}
	}
	
	@RequestMapping(value=Uris.SIGNUP, method=RequestMethod.POST)
	public String signup(@Valid UserWrapper form, BindingResult formBinding, WebRequest request) {
		if (formBinding.hasErrors()) {
			return null;
		}
		userController.registration(form);
		if (userController.exist(form.getUsername())){
			SignInUtils.signin(form.getUsername());
			providerSignInUtils.doPostSignUp(form.getUsername(), request);
			return "redirect:/home";
		}
		return null;
	}
	
	public static UserWrapper fromProviderUser(UserProfile providerUser) {
		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUsername(providerUser.getUsername());
		userWrapper.setEmail(providerUser.getEmail());
		userWrapper.setBirthDate(new GregorianCalendar(1979, 07, 22));
		return userWrapper;
	}
	
}
