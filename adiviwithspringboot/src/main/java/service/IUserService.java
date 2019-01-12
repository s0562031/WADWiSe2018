package service;

import java.util.List;

import bean.User;

public interface IUserService {

    List<User> getAllUsers();
    User getUserById(String id);
    boolean addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
