package business.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import business.wrapper.CreateTraining;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Training;
import data.entities.User;

@Controller
public class TrainingController {

	private TrainingDao trainingDao;

	private ReserveDao reserveDao;

	private CourtDao courtDao;

	private UserDao userDao;

	@Autowired
	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}

	@Autowired
	public void setReserveDao(ReserveDao reserveDao) {
		this.reserveDao = reserveDao;
	}

	@Autowired
	public void setCourtDao(CourtDao courtDao) {
		this.courtDao = courtDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean createTraining(CreateTraining createTraining) {
		Calendar starDate = (Calendar) createTraining.getStartDate().clone();
		while (createTraining.getEndDate().after(starDate)) {
			if (reserveDao.findByCourtAndDate(courtDao.findOne(createTraining.getCourtId()), starDate) != null) {
				return false;
			}
			starDate.add(Calendar.WEEK_OF_YEAR, 1);
		}
		Training training = new Training(userDao.findByUsernameOrEmail(createTraining.getTrainerUsername()),
				courtDao.findOne(createTraining.getCourtId()), createTraining.getStartDate(),
				createTraining.getEndDate());
		trainingDao.save(training);
		return true;
	}

	public boolean deleteTraining(int trainingId) {
		Training training = trainingDao.findById(trainingId);
		if (training == null) {
			return false;
		}
		trainingDao.delete(trainingId);
		return true;
	}

	public boolean registerTraining(String playerUsername, int trainingId) {
		Training training = trainingDao.findById(trainingId);
		if (training == null) {
			return false;
		}
		return training.addPlayer(userDao.findByUsernameOrEmail(playerUsername));
	}

	public boolean deleteTrainingPlayer(String playerUsername, int trainingId) {
		Training training = trainingDao.findById(trainingId);
		if (training == null) {
			return false;
		}
		return training.removePlayer(userDao.findByUsernameOrEmail(playerUsername));
	}

	public List<TrainingWrapper> showTrainings(Calendar date) {
		List<TrainingWrapper> trainingWrappers = new ArrayList<>();
		List<Training> trainings = trainingDao.findByStartDate(date);
		for (Training training : trainings) {
			List<String> playersUsername = new ArrayList<String>();
			for (User player : training.getPlayers()) {
				playersUsername.add(player.getUsername());
			}
			Calendar aux = Calendar.getInstance();
			aux.setTimeInMillis(training.getStartDate().getTimeInMillis() - training.getEndDate().getTimeInMillis());
			TrainingWrapper trainingWrapper = new TrainingWrapper(training.getTrainer().getUsername(),
					training.getStartDate(), training.getEndDate(), training.getCourt().getId(), aux.getWeekYear());
			trainingWrapper.setTrainingId(training.getId());
			trainingWrapper.setPlayersUsername(playersUsername);
			trainingWrappers.add(trainingWrapper);
		}
		return trainingWrappers;
	}

	public boolean exist(int trainingId) {
		return trainingDao.findOne(trainingId) != null;
	}

	public boolean playerExist(int trainingId, String playerUsername) {
		Training training = trainingDao.findById(trainingId);
		for (User player : training.getPlayers()) {
			if (player.getUsername().equals(playerUsername)) {
				return true;
			}
		}
		return false;
	}
}
