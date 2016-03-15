package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {
	
	@Autowired
    private DaosService daosService;
	
	@Autowired
	private TrainingDao trainingDao;

    @Autowired
    private ReserveDao reserveDao;
    
    @Autowired
    private CourtDao courtDao;
    
    @Test
    public void testFindByTrainer(){
    	User trainer = (User) daosService.getMap().get("trainer");
    	assertEquals(1,trainingDao.findByTrainer(trainer).size());
    }
    
    @Test
    public void testFindByTrainerAndCourt(){
    	User trainer = (User) daosService.getMap().get("trainer");
    	assertEquals(1,trainingDao.findByTrainerAndCourt(trainer, courtDao.findOne(3)).size()); 
    }
    
    @Test
    public void testFindByfindByStartDate(){
    	Calendar starDate = Calendar.getInstance();
    	starDate.add(Calendar.DAY_OF_YEAR, 2);
    	starDate.set(Calendar.HOUR_OF_DAY, 10);
    	starDate.set(Calendar.MINUTE, 0);
    	starDate.set(Calendar.SECOND, 0);
    	starDate.set(Calendar.MILLISECOND, 0);
    	
    	Calendar endDate = (Calendar) starDate.clone();
    	endDate.add(Calendar.WEEK_OF_YEAR, 2);
    	endDate.add(Calendar.HOUR_OF_DAY, 1);
    	
    	assertEquals(3,reserveDao.findByDateBetween(starDate, endDate).size());
    	assertNotNull(trainingDao.findByStartDate(starDate));
    }
    

}
