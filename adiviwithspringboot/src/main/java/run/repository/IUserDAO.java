package run.repository;

import java.util.List;
import run.user.User;


public interface IUserDAO {
	
    User getUserByUserId(String userId);
    List<User> getAllUsers();
    void addUser(User User);
    void updateUser(User User);
    void deleteUser(String userId);
	User getUserById(int userId);
    
}
