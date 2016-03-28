package data.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Training {
	
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn
	private User trainer;
	
	@ManyToOne
    @JoinColumn
    private Court court;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> players;
	
	private Calendar startDate;
	
	private Calendar endDate;
	
	private int numberOfPlayers;
	
	public Training(){
		
	}
	
	public Training(User trainer, Court court, Calendar startDate,Calendar endDate){
		this.trainer= trainer;
		this.court = court;
		this.startDate = startDate;
		this.endDate= endDate;
		this.players= new ArrayList<User>();
	}

	public int getId() {
		return id;
	}

	public User getTrainer() {
		return trainer;
	}

	public List<User> getPlayers() {
		return players;
	}

	public Court getCourt() {
		return court;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Training other = (Training) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", trainer=" + trainer + ", players=" + players + ", court=" + court
				+ ", startDate=" + startDate.getTime() + ", endDate=" + endDate.getTime() + "]";
	}
	
	public boolean addPlayer(User player){
			return this.players.add(player);
	}
	
	public boolean removePlayer(User player){
		return this.players.remove(player);
	}
}
