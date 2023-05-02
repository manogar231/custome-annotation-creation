package com.custome.serviceimpl;

import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.custome.entity.User;
import com.custome.repository.UserRepositroy;
import com.custome.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	Logger logger;
	
	@Autowired
	private UserRepositroy userRepositroy;
	
	public Object userlogin(String email, String password) throws Exception {
	
	Optional<User> user=userRepositroy.findUserByemail(email);		
	if(user.isEmpty()) {
		throw new Exception("User Not Found! please Sign Up");
	}	
	if(password==user.get().getPassword()) {
		logger.info("Login Success !"+user.get().getFirstname());
		}else {
			 logger.info("Password Incorrect..!");
		}
		return " ";
	}

	public Object forgotpassword() {
		
		return null;
	}
}
