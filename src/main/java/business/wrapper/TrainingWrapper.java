package business.wrapper;

import java.util.Calendar;
import java.util.List;

public class TrainingWrapper extends CreateTraining {

	private int trainingId;

	private List<String> playersUsername;

	public TrainingWrapper() {
		super();
	}

	public TrainingWrapper(int trainingId, List<String> playersUsername, String trainerUsername, Calendar startDate,
			Calendar endDate, int courtId) {
		super(trainerUsername, startDate, endDate, courtId);
		this.trainingId = trainingId;
		this.playersUsername = playersUsername;
		// TODO Auto-generated constructor stub
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
