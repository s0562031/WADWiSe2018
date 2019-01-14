package de.htw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import de.htw.model.Users;

public interface IUserDAO extends CrudRepository<Users, Integer>{
	
//    User getUserByUserId(String userId);
//    List<User> getAllUsers();
//    void addUser(User User);
//    void updateUser(User User);
//    void deleteUser(String userId);
//	User getUserById(int userId);
    
}
