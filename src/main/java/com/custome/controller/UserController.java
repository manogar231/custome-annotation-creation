package com.custome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.custome.dto.UserDto;
import com.custome.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public   Object userlogin(@RequestBody UserDto userDto) throws Exception {
		String tokenString =  userService.userlogin(userDto);
		return tokenString;
	}
	@PostMapping("/forgot-password")
	public Object forgotpassword(@RequestBody UserDto userDto) {
		return userService.forgotpassword(userDto);
	}
	@PutMapping("/changepassword/{id}")
	public Object otpcheckingfunction(@PathVariable int id,@RequestBody UserDto userDto){
		return userService.otpcheckandchangepassoword(id, userDto);
	}
}
