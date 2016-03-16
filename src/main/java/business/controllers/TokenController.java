package business.controllers;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import data.daos.TokenDao;
import data.daos.UserDao;
import data.entities.Token;
import data.entities.User;

@Controller
@Transactional
public class TokenController {

    private TokenDao tokenDao;

    private UserDao userDao;

    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String login(String username) {
        User user = userDao.findByUsernameOrEmail(username);
        assert user != null;
        Token token = new Token(user);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR_OF_DAY, 1);
        token.setExpirationDate(date);
        tokenDao.save(token);
        return token.getValue();
    }
}
