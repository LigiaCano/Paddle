package data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.daos.AuthorizationDao;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TokenDao;
import data.daos.TrainingDao;
import data.daos.UserConnectionDao;
import data.daos.UserDao;

@Service
public class DataService {
    
    @Autowired
    private Populate populate;

    @Autowired
    private ReserveDao reserveDao;
    
    @Autowired
    private AuthorizationDao authorizationDao;
    
    @Autowired
    private TokenDao tokenDao;
    
    @Autowired
    private CourtDao courtDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private TrainingDao trainingDao;
    
	@Autowired
	private UserConnectionDao userConnectionDao;
    

    public void deleteAllExceptAdmin(){
    	trainingDao.deleteAll();
        reserveDao.deleteAll();
        authorizationDao.deleteAll();
        tokenDao.deleteAll();
        courtDao.deleteAll();
        userDao.deleteAll();
        userConnectionDao.deleteAll();
        populate.createDefaultAdmin();
        populate.createDefaultTrainer();
        
    }

}
