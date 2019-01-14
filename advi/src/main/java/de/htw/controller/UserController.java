package de.htw.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.model.Users;
import de.htw.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/addUser")
	private void addUser(Users user) {
		service.addUser(user);
	}
	
	@RequestMapping("/update")
	private void update (@RequestParam int id, @RequestParam String password, @RequestParam String firstname, @RequestParam String lastname, @RequestParam boolean isadmin) {
		Users user = new Users(id,lastname,firstname,password,isadmin);
		service.updateUser(user);
	}
	
	/**
	 * gets JSON from user if id and password match with entity in DB
	 * @param id
	 * @param password
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/login")
	private ResponseEntity<String> login (@RequestParam int id, @RequestParam String password) throws JsonProcessingException {
		
		Users user = service.loginUser(id, password);
		
	    System.out.println("loggedin");
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
	 * creates JSON from User
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	private String convertToJson(Users user) throws JsonProcessingException{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(user); 
	}
	
}
