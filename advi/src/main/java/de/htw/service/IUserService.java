package de.htw.service;

import de.htw.model.Users;


public interface IUserService {

	Users loginUser(int userId, String password);

}
