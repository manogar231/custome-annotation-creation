package com.custome.serviceimpl;

import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.custome.dto.UserDto;
import com.custome.entity.User;
import com.custome.repository.UserRepository;
import com.custome.service.UserService;
import com.custome.util.JwtTokenGenerator;
import com.custome.util.OtpGenerator;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private Logger logger;
	@Autowired
	private UserRepository userRepositroy;
	@Autowired
	private OtpGenerator otpGenerator;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	JwtTokenGenerator generator;	
	@Autowired
	private ModelMapper modelMapper;

	public String userlogin(UserDto userDto) throws Exception {
		Optional<User> user = userRepositroy.findUserBymail(userDto.getmail());
	   User user1=	user.get();
		if (user.isEmpty()) {
			throw new Exception("User Not Found! please Sign Up");
		}
		if (user.get().getPassword().equals(userDto.getPassword())) {
			logger.info("Login Success !");
			String tokenString = generator.generateToken(user1.getMail());
			return tokenString;
		} else {
			return HttpStatus.UNAUTHORIZED.toString();
		}
	}
	
	public Object forgotpassword(UserDto userDto) {
		User user = userRepositroy.findUserBymail(userDto.getmail()).get();
		if (user==null) {
			return HttpStatus.UNAUTHORIZED;
		}
		String	resetOtp=otpGenerator.generateOTP();		
    	user.setResetOtp(resetOtp);
		userRepositroy.save(user);				
        UserDto userDto1=modelMapper.map(user, UserDto.class);
		String url = "http://localhost:8083/sendmail";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDto> request = new HttpEntity<>(userDto1, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return "OTP Send Success";
	}
	
	public Object otpcheckandchangepassoword(int id, UserDto userDto) {		
       User user=userRepositroy.findById(id).get();		
       if(user==null) {
    	   return HttpStatus.NOT_FOUND;
       }
	   if(user.getResetOtp().equals(userDto.getResetOtp()) ){
		   if(user.getPassword().equals(userDto.getPassword())) {
			   return "Password Already Exits!Try Another One";
		   }else {
			user.setPassword(userDto.getPassword()); 
			userRepositroy.save(user);
		   }		   
	   }else {
		   return "OTP Not Correct";
	   }
		return "Password Changed Successfully !!";
	}
}
