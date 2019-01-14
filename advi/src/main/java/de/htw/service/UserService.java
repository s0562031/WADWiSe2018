package de.htw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw.model.Users;
import de.htw.repository.IUserDAO;


@Service("userService")
public class UserService implements IUserService {

    @Autowired
    private IUserDAO repository;
    

	@Override
	public List<Users> getAllUsers() {
		return (List<Users>) repository.findAll();
	}

	@Override
	public Users getUserById(String id) {
		//return repository.findById(id);
		return null;
	}

	@Override
	public void addUser(Users user) {
		repository.save(user);
	}

	@Override
	public void updateUser(Users user) {
		Optional<Users> usertemp = repository.findById(user.getId());
		repository.deleteById(usertemp.get().getId());
		repository.save(user);		
	}

	@Override
	public void deleteUser(Users user) {
		repository.delete(user);
		
	}
}