package data.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import business.entities.Authorization;
import business.entities.User;
import business.utils.Role;

public interface AuthorizationDao extends JpaRepository<Authorization, Integer> {
    @Query("select authorization.role from Authorization authorization where authorization.user = ?1")
    List<Role> findRoleByUser(User user);
}
