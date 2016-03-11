package data.entities;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        assertTrue(token.getValue().length() > 20);
    }
    
    @Test
    public void testTokenExpiredDate() {
        User user = new User("u1", "u1@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR_OF_DAY, 1);
        token.setExpirationDate(date);
        assertTrue(Calendar.getInstance().getTime().before(token.getExpirationDate().getTime()));
    }
    

}
