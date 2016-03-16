package web.presenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPresenter {

	public IndexPresenter() {

	}

	@ModelAttribute("now")
	public String now() {
		return new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss").format(new Date());
	}
	
	@ModelAttribute("welcome")
	public String welcome() {
		return "Welcome Club Paddle";
	}
	
	@RequestMapping(Uris.HOME)
	public String home(Model model) {
		return "/home";
	}

}
