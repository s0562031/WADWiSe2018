package de.htw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import de.htw.model.Users;



@Service("userService")
public class UserService implements IUserService {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Users loginUser(int userId, String password) {
		String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
		RowMapper<Users> rowMapper = new BeanPropertyRowMapper<Users>(Users.class);
		Users user = jdbcTemplate.queryForObject(sql, rowMapper, userId, password);
		return user;
	}
	

}