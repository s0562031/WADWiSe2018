package run.repository;

import java.util.List;
import run.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import run.user.UserRowMapper;

@Repository
public class UserDBDAO implements IUserDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	public UserDBDAO(JdbcTemplate ds) {
		this.jdbcTemplate = ds;
	}
	
	
	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE id = " + userId;
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, userId);
		return user;
	}
	
	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users";
        //RowMapper<User> rowMapper = new BeanPropertyRowMapper<Article>(Article.class);
		RowMapper<User> rowMapper = new UserRowMapper();
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User User) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUser(User User) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}
}