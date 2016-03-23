package business.wrapper;

import java.util.Calendar;

public class CreateTraining {

	private String trainerUsername;

	private Calendar startDate;

	private Calendar endDate;

	private int numberWeek;

	private int courtId;

	public CreateTraining() {

	}

	public CreateTraining(Calendar startDate, int numberWeek, int courtId) {
		this.startDate = startDate;
		this.numberWeek = numberWeek;
		this.courtId = courtId;
	}

	public CreateTraining(String trainerUsername, Calendar startDate, Calendar endDate, int courtId) {
		this.trainerUsername = trainerUsername;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courtId = courtId;
	}

	public String getTrainerUsername() {
		return trainerUsername;
	}

	public void setTrainerUsername(String trainerUsername) {
		this.trainerUsername = trainerUsername;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public int getNumberWeek() {
		return numberWeek;
	}

	public int getCourtId() {
		return courtId;
	}
	
	@Override
	public String toString() {
		return "CreateTraining [trainerUsername=" + trainerUsername + ", startDate=" + startDate.getTime() + ", endDate="
				+ endDate.getTime() + ", numberWeek=" + numberWeek + ", courtId=" + courtId + "]";
	}

}
