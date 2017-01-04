package data.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.UserConnection;

public interface UserConnectionDao extends JpaRepository<UserConnection,String>{


}
