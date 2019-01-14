package de.htw.service;

import java.util.List;
import java.util.Optional;

import de.htw.model.Users;



public interface IUserService {

    List<Users> getAllUsers();
    Optional<Users> getUserById(int id);
    void addUser(Users user);
    void updateUser(Users user);
    void deleteUser(Users user);
    
    Users getUserByIdWithJdbc(int id);
	Users deleteUserWithJdbc(int userId);
	Users loginUser(int userId, String password);
}
