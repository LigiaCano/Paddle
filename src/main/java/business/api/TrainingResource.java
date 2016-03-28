package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistPlayerInTraining;
import business.api.exceptions.AlreadyFullTrainingException;
import business.api.exceptions.InvalidDateException;
import business.api.exceptions.InvalidTrainingCreateException;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.NotFoundPlayerInTraining;
import business.api.exceptions.NotFoundTrainingIdException;
import business.controllers.CourtController;
import business.controllers.ReserveController;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;
import business.wrapper.CreateTraining;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

	private TrainingController trainigController;

	private ReserveController reserveController;

	private CourtController courtController;

	@Autowired
	public void setTrainigController(TrainingController trainigController) {
		this.trainigController = trainigController;
	}

	@Autowired
	public void setReserveController(ReserveController reserveController) {
		this.reserveController = reserveController;
	}

	@Autowired
	public void setCourtController(CourtController courtController) {
		this.courtController = courtController;
	}

	private void validateDay(Calendar day) throws InvalidDateException {
		Calendar calendarDay = Calendar.getInstance();
		calendarDay.add(Calendar.DAY_OF_YEAR, -1);
		if (calendarDay.after(day)) {
			throw new InvalidDateException("La fecha no puede ser un día pasado");
		}
		if (!reserveController.rightTime(day.get(Calendar.HOUR_OF_DAY))) {
			throw new InvalidDateException(day.get(Calendar.HOUR_OF_DAY) + "hora fuera de rango");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createTraining(@AuthenticationPrincipal User trainer, @RequestBody CreateTraining createTraining)
			throws NotFoundCourtIdException, InvalidTrainingCreateException, InvalidDateException {
		if (!courtController.exist(createTraining.getCourtId())) {
			throw new NotFoundCourtIdException("" + createTraining.getCourtId());
		}
		if (createTraining.getNumberWeek() < 1) {
			throw new InvalidTrainingCreateException("Nª de semanas debe ser superior a 0");
		}
		Calendar startDate = createTraining.getStartDate();
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		this.validateDay(startDate);

		Calendar endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.WEEK_OF_YEAR, createTraining.getNumberWeek() - 1);
		endDate.add(Calendar.HOUR_OF_DAY, 1);
		createTraining.setTrainerUsername(trainer.getUsername());
		createTraining.setEndDate(endDate);
		if (!trainigController.createTraining(createTraining)) {
			throw new InvalidTrainingCreateException(createTraining.getCourtId() + ", fecha de inicio: "
					+ createTraining.getStartDate() + ", fecha fin:" + createTraining.getEndDate());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteTraining(@RequestParam(required = true) int id) throws NotFoundTrainingIdException {
		if (!trainigController.deleteTraining(id)) {
			throw new NotFoundTrainingIdException("id: " + id);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<TrainingWrapper> showTrainings(@RequestParam(required = false) Long day) {
		Calendar calendarDay = Calendar.getInstance();
		if (day != null) {
			calendarDay.setTimeInMillis(day);
		}
		return trainigController.showTrainings(calendarDay);
	}

	@RequestMapping(value = Uris.ID + Uris.PLAYERS, method = RequestMethod.POST)
	public void registerTraining(@AuthenticationPrincipal User player, @PathVariable int id)
			throws NotFoundTrainingIdException, AlreadyExistPlayerInTraining, AlreadyFullTrainingException {
		if (!trainigController.exist(id)) {
			throw new NotFoundTrainingIdException("id: " + id);
		}
		if (trainigController.playerExist(id, player.getUsername())) {
			throw new AlreadyExistPlayerInTraining(player.getUsername());
		}
		if (!trainigController.registerTraining(player.getUsername(), id)) {
			throw new AlreadyFullTrainingException("id: " + id);
		}
	}

	@RequestMapping(value = Uris.ID + Uris.PLAYERS, method = RequestMethod.DELETE)
	public void deleteTrainigPlayer(@PathVariable int id, @RequestParam(required = true) String playerUsername)
			throws NotFoundTrainingIdException, NotFoundPlayerInTraining {
		if (!trainigController.exist(id)) {
			throw new NotFoundTrainingIdException("id: " + id);
		}
		if (!trainigController.playerExist(id, playerUsername)) {
			throw new NotFoundPlayerInTraining(playerUsername);
		}
		if (!trainigController.deleteTrainingPlayer(playerUsername, id)) {
			throw new NotFoundTrainingIdException("id: " + id);
		}
	}

}
