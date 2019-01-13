package controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bean.User;
import service.IUserService;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	

    // http://localhost:8080/loginMVC/login?userId=admin&password=password
	
	/**
	 * gets User by his ID and returns JSON from user
	 * @param id
	 * @param password
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ResponseEntity<String> loginUser (@RequestParam("id") String id, @RequestParam("password") String password) throws IOException {		

	    //Now: Business logic, verify that user exists...
	    User user = userService.getUserById(id);
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (user == null || !user.getPassword().equals(password)) {
	        return new ResponseEntity<String>("No such userId/password combo", 
	                responseHeaders, HttpStatus.UNAUTHORIZED);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertToJson(user);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }		
	}
	
	/**
	 * provides List of Users from DB
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/contacts", method=RequestMethod.GET)
	public ResponseEntity<String> getContacts () throws IOException {		

	    //Now: Business logic, verify that user exists...
	    List<User> user = userService.getAllUsers();
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (user == null) {
	        return new ResponseEntity<String>("No contacts found", 
	                responseHeaders, HttpStatus.UNAUTHORIZED);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertListToJson(user);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }		
	}
	
	/**
	 * creates JSON from User
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	private String convertToJson(User user) throws JsonProcessingException{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(user); 
	}
	
	/**
	 * creates JSON from List of Users
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	private String convertListToJson(List<User> user) throws JsonProcessingException{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(user); 
	}
	
	//TODO: Save new User/ Delete User
	
}
