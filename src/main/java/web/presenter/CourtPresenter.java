package web.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.CourtController;
import business.wrapper.CourtState;

@Controller
public class CourtPresenter {

	private CourtController courtController;

	@Autowired
	public void setCourtController(CourtController courtController) {
		this.courtController = courtController;
	}

	public CourtPresenter() {

	}

	@RequestMapping(value = Uris.CREATECOURT, method = RequestMethod.GET)
	public String createCourt(Model model) {
		model.addAttribute("court", new CourtState());
		return "createCourt";
	}

	@RequestMapping(value = Uris.CREATECOURT, method = RequestMethod.POST)
	public String createCourtSubmit(@ModelAttribute CourtState court, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			if (this.courtController.createCourt(court.getCourtId())) {
				model.addAttribute("court", court);
				return "registrationCourtSuccess";
			} else {
				bindingResult.rejectValue("courtId", "error.court", "Existing court");
			}
		}
		model.addAttribute("court", court);
		return "createCourt";
	}

	@RequestMapping(Uris.SHOWCOURTS)
	public ModelAndView listUsers(Model model) {
		ModelAndView modelAndView = new ModelAndView("/showCourts");
		modelAndView.addObject("courtList", courtController.showCourts());
		return modelAndView;
	}

	@RequestMapping(value = Uris.UPDATECOURT + "/{id}", method = RequestMethod.GET)
	public String updateCourt(@PathVariable int id, Model model) {
		model.addAttribute("court", courtController.showCourt(id));
		return "updateCourt";
	}

	@RequestMapping(value = Uris.UPDATECOURT, method = RequestMethod.POST)
	public String updateCourtSubmit(@ModelAttribute CourtState court, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			if (this.courtController.changeCourtActivation(court.getCourtId(), court.getActive())) {
				model.addAttribute("courtList", courtController.showCourts());
				return "showCourts";
			}
		}
		model.addAttribute("court", court);
		return "updateCourt";
	}

}
