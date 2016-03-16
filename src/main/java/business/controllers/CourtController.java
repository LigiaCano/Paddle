package business.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.CourtState;
import data.daos.CourtDao;
import data.entities.Court;

@Controller
public class CourtController {

    private CourtDao courtDao;

    @Autowired
    public void setCourtDao(CourtDao courtDao) {
        this.courtDao = courtDao;
    }

    public boolean createCourt(int courtId) {
        if (courtDao.exists(courtId)) {
            return false;
        } else {
            courtDao.save(new Court(courtId));
            return true;
        }
    }

    public boolean changeCourtActivation(int id, boolean active) {
        Court court = courtDao.findOne(id);
        if (court == null) {
            return false;
        } else {
            court.setActive(active);
            courtDao.save(court);
            return true;
        }
    }

    public List<CourtState> showCourts() {
        List<CourtState> courtStateList = new ArrayList<>();
        for (Court court : courtDao.findAll()) {
            courtStateList.add(new CourtState(court));
        }
        return courtStateList;
    }

    public boolean exist(int courtId) {
        return courtDao.findOne(courtId) != null;
    }
    
    public CourtState showCourt(int courtId){
    	CourtState courtState = new CourtState(courtDao.findOne(courtId));
    	return courtState;
    }

}
