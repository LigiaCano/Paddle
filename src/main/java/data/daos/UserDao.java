package data.daos;

import java.util.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.User;

public interface UserDao extends JpaRepository<User, Integer> {
    
    @Query("select token.user from Token token where token.value = ?1")
    public User findByTokenValue(String tokenValue);

    @Query("select user from User user where user.username = ?1 or user.email = ?1")
    public User findByUsernameOrEmail(String id);
    
    @Query("select token.user from Token token where token.value = ?1 and token.expirationDate >= ?2")
    public User findByTokenValueNonExpiredToken(String tokenValue, Calendar date);

    @Query("select token.user from Token token join token.user user  where (user.username = ?1 or user.email = ?1) and token.expirationDate >= ?2")
    public User findByUsernameOrEmailNonExpiredToken(String id, Calendar date);

}
