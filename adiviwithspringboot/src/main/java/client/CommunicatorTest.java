package client;

import java.util.List;

import bean.User;
import repository.UserDBDAO;
import service.UserService;

public class CommunicatorTest {
	
	public static void main(String[] args) {
		
		UserDBDAO userlist = new UserDBDAO();
		
		System.out.println(userlist);
		
		
		UserService us = new UserService();
		
		User userone = us.getUserById("1");
		
		System.out.println(userone);
		
	}

}
