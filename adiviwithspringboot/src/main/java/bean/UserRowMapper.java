package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
    
	//mapping of: id integer PRIMARY KEY, lastname VARCHAR(128), firstname VARCHAR(128), password VARCHAR(128), isadmin boolean
	
    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
    	
        User user = new User();
        user.setId(row.getInt("id"));
        user.setPassword(row.getString("password"));
        user.setLastname(row.getString("lastname"));
        user.setFirstname(row.getString("firstname"));
        user.setIsAdmin(row.getBoolean("isadmin"));
        
        return user;
    }

}