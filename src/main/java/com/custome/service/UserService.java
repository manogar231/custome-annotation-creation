package com.custome.service;

import org.springframework.stereotype.Service;

import com.custome.dto.UserDto;

@Service
public interface UserService {

  String userlogin(UserDto usetDto)throws Exception  ;

  Object forgotpassword(UserDto userDto);

  Object otpcheckandchangepassoword(int id, UserDto userDto);

}
