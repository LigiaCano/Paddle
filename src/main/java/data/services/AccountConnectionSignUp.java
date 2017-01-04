package data.services;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import business.controllers.UserController;
import business.wrapper.UserWrapper;

@Service
public class AccountConnectionSignUp implements ConnectionSignUp{
	private  UserController userController;
	
	@Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

	@Override
	public String execute(Connection<?> connection) {
		 UserProfile providerUser = connection.fetchUserProfile();
		 UserWrapper userWrapper = new UserWrapper();
		 userWrapper.setUsername(providerUser.getUsername());
		 userWrapper.setEmail(providerUser.getEmail());
		 userWrapper.setBirthDate(new GregorianCalendar(1979, 07, 22));
		 userController.registration(userWrapper);
			if (userController.exist(userWrapper.getUsername())){
				return userWrapper.getUsername();
			}
		return null;
	}

}
