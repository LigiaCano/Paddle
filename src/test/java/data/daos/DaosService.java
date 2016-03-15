package data.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.entities.Authorization;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Role;
import data.entities.Token;
import data.entities.Training;
import data.entities.User;
import data.services.DataService;

@Service
public class DaosService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private CourtDao courtDao;

    @Autowired
    private ReserveDao reserveDao;
    
    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DataService genericService;

    private Map<String, Object> map;

    @PostConstruct
    public void populate() {
        map = new HashMap<>();
     
        User[] users = this.createPlayers(0, 4);
        for (User user : users) {
            map.put(user.getUsername(), user);
        }
        for (Token token : this.createTokens(users)) {
            map.put("t" + token.getUser().getUsername(), token);
        }
        for (User user : this.createPlayers(4, 4)) {
            map.put(user.getUsername(), user);
        }
        this.createCourts(1, 4);
        
        User trainer = createTrainer();
        map.put(trainer.getUsername(),trainer);
        this.createTraining(trainer, courtDao.findOne(3), users);
        
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 4; i++) {
            date.add(Calendar.HOUR_OF_DAY, 1);
            reserveDao.save(new Reserve(courtDao.findOne(i+1), users[i], date));
        }
    }

    public User[] createPlayers(int initial, int size) {
        User[] users = new User[size];
        for (int i = 0; i < size; i++) {
            users[i] = new User("u" + (i + initial), "u" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
            userDao.save(users[i]);
            authorizationDao.save(new Authorization(users[i], Role.PLAYER));
        }
        return users;
    }
    
    public User createTrainer(){
    	 User trainer = new User("trainer", "trainer@gmail.com", "t", Calendar.getInstance());
    	 userDao.save(trainer);
         return trainer;
    }

    public List<Token> createTokens(User[] users) {
        List<Token> tokenList = new ArrayList<>();
        Token token;
        for (User user : users) {
            token = new Token(user);
            Calendar date = Calendar.getInstance();
            date.add(Calendar.HOUR_OF_DAY, 1);
            token.setExpirationDate(date);
            tokenDao.save(token);
            tokenList.add(token);
        }
        return tokenList;
    }

    public void createCourts(int initial, int size) {
        for (int id = 0; id < size; id++) {
            courtDao.save(new Court(id + initial));
        }
    }
    
    public void createTraining(User trainer, Court court,User[] players){
    	Calendar starDate = Calendar.getInstance();
    	starDate.add(Calendar.DAY_OF_YEAR, 2);
    	starDate.set(Calendar.HOUR_OF_DAY, 10);
    	starDate.set(Calendar.MINUTE, 0);
    	starDate.set(Calendar.SECOND, 0);
    	starDate.set(Calendar.MILLISECOND, 0);
    	
    	Calendar endDate = (Calendar) starDate.clone();
    	endDate.add(Calendar.WEEK_OF_YEAR, 2);
    	endDate.add(Calendar.HOUR_OF_DAY, 1);
    	Training training = new Training(trainer, court, starDate, endDate);
    	for (int i = 0; i < 4; i++) {
    		 training.addPlayer(players[i]);
    	}
    	trainingDao.save(training);
    	
    	 while (endDate.after(starDate) ){
    		 reserveDao.save(new Reserve(court, trainer, starDate));
    		 starDate.add(Calendar.WEEK_OF_YEAR, 1);
    	 }
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void deleteAll() {
        genericService.deleteAllExceptAdmin();
    }
}
