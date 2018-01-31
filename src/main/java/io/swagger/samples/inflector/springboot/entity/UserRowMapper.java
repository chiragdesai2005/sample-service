package io.swagger.samples.inflector.springboot.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setName(rs.getString("name"));
		user.setDob(rs.getString("dob"));
		return user;
	}

}
