package de.htw.service;

import java.util.List;

import de.htw.model.Users;



public interface IUserService {

    List<Users> getAllUsers();
    Users getUserById(String id);
    void addUser(Users user);
    void updateUser(Users user);
    void deleteUser(Users user);
}
