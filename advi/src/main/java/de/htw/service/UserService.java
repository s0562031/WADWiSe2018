package de.htw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	public Optional<Users> getUserById(int id) {
		return repository.findById(id);
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
	
	//########################
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Users getUserByIdWithJdbc(int userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		RowMapper<Users> rowMapper = new BeanPropertyRowMapper<Users>(Users.class);
		Users user = jdbcTemplate.queryForObject(sql, rowMapper, userId);
		return user;
	}
	
	@Override
	public Users loginUser(int userId, String password) {
		String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
		RowMapper<Users> rowMapper = new BeanPropertyRowMapper<Users>(Users.class);
		Users user = jdbcTemplate.queryForObject(sql, rowMapper, userId, password);
		return user;
	}
	
	@Override
	public Users deleteUserWithJdbc(int userId) {
		String sql = "DELETE FROM users WHERE id = ?";
		RowMapper<Users> rowMapper = new BeanPropertyRowMapper<Users>(Users.class);
		Users user = jdbcTemplate.queryForObject(sql, rowMapper, userId);
		return user;
	}
}