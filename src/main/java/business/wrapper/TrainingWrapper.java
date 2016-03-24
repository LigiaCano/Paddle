package business.wrapper;

import java.util.Calendar;
import java.util.List;

public class TrainingWrapper extends CreateTraining {

	private int trainingId;

	private List<String> playersUsername;

	public TrainingWrapper() {
		super();
	}

	public TrainingWrapper(String trainerUsername, Calendar startDate, Calendar endDate, int courtId, int numberWeek) {
		super(trainerUsername, startDate, endDate, courtId, numberWeek);
	}



	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public List<String> getPlayersUsername() {
		return playersUsername;
	}

	public void setPlayersUsername(List<String> playersUsername) {
		this.playersUsername = playersUsername;
	}

	@Override
	public String toString() {
		return "TrainingWrapper [trainingId=" + trainingId + ", playersUsername=" + playersUsername + "]";
	}

}
