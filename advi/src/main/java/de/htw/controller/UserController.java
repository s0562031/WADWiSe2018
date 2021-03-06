package de.htw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.model.Contacts;
import de.htw.model.Users;
import de.htw.service.ContactsService;
import de.htw.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService u_service;
	
	@Autowired
	private ContactsService c_service;
	
	//############# users ################
	
	/**
	 * gets JSON from user if id and password match with entity in DB
	 * @param id
	 * @param password
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/login")
	private ResponseEntity<String> login (@RequestParam int id, @RequestParam String password) throws JsonProcessingException {
		
		Users user = u_service.loginUser(id, password);
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
	
	
	//############ contacts ##############
	
	/**
	 * calls addContact on ContactService
	 * @param contact
	 * @return addes Contact
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/addContact")
	private ResponseEntity<String> addContact(Contacts contact) throws JsonProcessingException {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
//		// check if id taken
//		if(c_service.getContactById(contact.getId()) != null) {
//			return new ResponseEntity<String>(responseHeaders, HttpStatus.NOT_FOUND); 
//		}
		
		
		Contacts validation_contact = c_service.addContact(contact);
		
		
	    if (validation_contact == null) {
	        return new ResponseEntity<String>("Adding failed", responseHeaders, HttpStatus.NOT_FOUND);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertToJson(validation_contact);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }	
	}
	
	
	/**
	 * calls deleteContact on ContactService
	 * @param id
	 */
	@RequestMapping("/deleteContact")
	private ResponseEntity<String> deleteContact(@RequestParam int id) {
		
		c_service.deleteContact(id);
				
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (c_service.getContactById(id) == null) {
	        return new ResponseEntity<String>(responseHeaders, HttpStatus.NO_CONTENT); 
	    }
	    else return new ResponseEntity<String>(responseHeaders, HttpStatus.NOT_FOUND); 
	}

	
	/**
	 * gets JSON from user if entity with that id can be found in DB
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/getContact")
	private ResponseEntity<String> getContact(@RequestParam int id) throws JsonProcessingException {
		
		Contacts contact = c_service.getContactById(id);		
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (contact == null) {
	        return new ResponseEntity<String>("No User with that Id found", 
	                responseHeaders, HttpStatus.UNAUTHORIZED);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertToJson(contact);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }		
	}
	
	/**
	 * gets JSON from user if entity with that id can be found in DB
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/getAllContacts")
	private ResponseEntity<String> getAllContacts() throws JsonProcessingException {
		
		List<Contacts> contactlist = c_service.getAllContacts();		
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (contactlist == null) {
	        return new ResponseEntity<String>("No Users found", 
	                responseHeaders, HttpStatus.UNAUTHORIZED);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertListToJson(contactlist);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }		
	}
	
	/**
	 * gets JSON from user if entity with that id can be found in DB
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/getAllPublicContacts")
	private ResponseEntity<String> getAllPublicContacts() throws JsonProcessingException {
		
		List<Contacts> contactlist = c_service.getAllPublicContacts();		
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (contactlist == null) {
	        return new ResponseEntity<String>("No Users found", 
	                responseHeaders, HttpStatus.UNAUTHORIZED);
	    } else {
	        responseHeaders.add("Content-Type", "application/json");
	        String json = convertListToJson(contactlist);
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK); 
	    }		
	}
	
	/**
	 * updates Contact 
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param address
	 * @param city
	 * @param postcode
	 * @param country
	 * @param privat
	 */
	@RequestMapping("/updateContact")
	private void update (@RequestParam int id, @RequestParam String lastname, @RequestParam String firstname, @RequestParam String address, @RequestParam String city, @RequestParam int postcode, @RequestParam String country, @RequestParam boolean privat) {
		Contacts contact = new Contacts(id,lastname,firstname,address, city, postcode, country, privat);
		c_service.updateContact(contact);
	}
	

	
	//####### JSON parsing ############
	
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
	
	/**
	 * creates JSON from User
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	private String convertToJson(Contacts contact) throws JsonProcessingException{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(contact); 
	}
	
	/**
	 * creates JSON from User
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	private String convertListToJson(List<Contacts> contactlist) throws JsonProcessingException{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(contactlist); 
	}
	
}
