package com.custome.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {

	public String generateOTP() {
		int length=6;
		String numbers="0123456789";
		 Random random = new Random();  
		    char[] otp = new char[length];  
		    for (int i = 0; i < length; i++) {  
		        otp[i] = numbers.charAt(random.nextInt(numbers.length()));  
		    }  
		    return new String(otp);  
		}  
	}

