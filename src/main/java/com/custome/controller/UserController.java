package com.custome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custome.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> userlogin(@PathVariable String email,String password) throws Exception {
		return ResponseEntity.ok().body(userService.userlogin(email, password));
	}
	
	public Object forgotpassword() {
		return userService.forgotpassword();
	}
}
