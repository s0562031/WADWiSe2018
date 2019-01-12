package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
    
    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
        User user = new User();
        user.setId(row.getInt("id"));
        user.setPassword(row.getString("password"));
        user.setFirstname(row.getString("firstname"));
        user.setLastname(row.getString("lastname"));
        user.setIsAdmin(row.getBoolean("isddmin"));
        return user;
    }

}