package web.presenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninPresenter {
	
	@RequestMapping(value = Uris.SIGNIN, method = RequestMethod.GET)
	public void signin() {
	}

}
