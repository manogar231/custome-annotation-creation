package com.custome.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderService {
	
	@Autowired
	private JavaMailSender sendmail; 

	public void sendmail() {
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("");
		message.setTo("");
		message.setText("");
		message.setSubject("");
		sendmail.send(message);
	}
	
	
}
