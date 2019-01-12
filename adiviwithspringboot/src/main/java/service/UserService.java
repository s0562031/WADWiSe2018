package service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bean.User;
import repository.UserDBDAO;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDBDAO repository;

	@Override
	public List<User> getAllUsers() {
		return repository.getAllUsers();
	}

	@Override
	public User getUserById(String id) {
		User user = repository.getUserByUserId(id);
		return user;
	}

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}
}