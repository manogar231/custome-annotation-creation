package com.custome.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

  Object userlogin(String email, String password)throws Exception  ;

  Object forgotpassword();

}
