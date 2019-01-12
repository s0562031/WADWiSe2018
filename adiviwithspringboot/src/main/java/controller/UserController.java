package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.User;
import service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("user/{id}")
	public ResponseEntity<User> getUserByUserId(@PathVariable("id") Integer id) {
		User user = userService.getUserById(id.toString());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userlist = userService.getAllUsers();
		return new ResponseEntity<List<User>>(userlist,HttpStatus.OK);
		
	}

}
