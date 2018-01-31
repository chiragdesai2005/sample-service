package io.swagger.samples.inflector.springboot.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.swagger.samples.inflector.springboot.entity.User;
import io.swagger.samples.inflector.springboot.entity.UserRowMapper;

@Component
public class UserResource implements Resource {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<Link> getLinks() {
		return Collections.emptyList();
	}

	public String getSurname() {
		String[] name = getUserDetails();
		if(name != null && name.length > 0) {
			return name[0];
		}
		return null;
	}
	
	public String getGivenName() {
		String[] name = getUserDetails();
		if(name != null && name.length > 1) {
			return name[1].trim();
		}
		return null;
	}
	
	public String getDoB() throws ParseException {
		Optional<User> user = getuser();
		if(user.isPresent() && user.get().getDob() != null) {
			return sdf.format(user.get().getDob());
		}
		return null;
	}
	
	private String[] getUserDetails(){
		Optional<User> user = getuser();
		if(user.isPresent() && user.get().getName() != null) {
			String[] userName = user.get().getName().split(",");
			return userName;
		}
		return null;
	}
	
	private Optional<User> getuser(){
		String sql = "SELECT name,dob FROM UserDetails WHERE ROWNUM = 1";
		return Optional.of(jdbcTemplate.queryForObject(sql, null, new UserRowMapper()));
	}


}
