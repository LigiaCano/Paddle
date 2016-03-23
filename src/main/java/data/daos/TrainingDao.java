package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public interface TrainingDao extends JpaRepository<Training, Integer> {
	
	Training findById(int id);
	
	@Query("select training from Training training where training.startDate >=?1")
	List<Training> findByStartDate(Calendar date);
	
	List<Training> findByTrainer(User trainer);
	
	List<Training> findByCourt(Court court);
	
	List<Training> findByTrainerAndCourt(User trainer, Court court);
	
	Training findByTrainerAndStartDate(User trainer, Calendar date);

}
