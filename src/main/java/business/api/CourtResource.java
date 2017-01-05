package business.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistCourtIdException;
import business.api.exceptions.NotFoundCourtIdException;
import business.controllers.CourtController;
import business.wrapper.CourtState;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.COURTS)
public class CourtResource {

	private CourtController courtController;

	@Autowired
	public void setCourtController(CourtController courtController) {
		this.courtController = courtController;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createCourt(@RequestParam(required = true) int id) throws AlreadyExistCourtIdException {
		if (!this.courtController.createCourt(id)) {
			throw new AlreadyExistCourtIdException();
		}

	}

	@RequestMapping(value = Uris.ID + Uris.ACTIVE, method = RequestMethod.POST)
	public void changeCourtActivationTrue(@PathVariable int id) throws NotFoundCourtIdException {
		if (!courtController.changeCourtActivation(id, true)) {
			throw new NotFoundCourtIdException("id: " + id);
		}
	}

	@RequestMapping(value = Uris.ID + Uris.ACTIVE, method = RequestMethod.DELETE)
	public void changeCourtActivationFalse(@PathVariable int id) throws NotFoundCourtIdException {
		if (!courtController.changeCourtActivation(id, false)) {
			throw new NotFoundCourtIdException("id: " + id);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<CourtState> showCourts() {
		return courtController.showCourts();
	}

}
