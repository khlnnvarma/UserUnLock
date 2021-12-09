package com.java.util;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.java.customexception.CommonException;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public Boolean sendMail(String to,String subject,String text) throws CommonException
	{
		Logger logger = LoggerFactory.getLogger(EmailUtils.class);
		Boolean mailStatus = false;
		
		MimeMessage message = javaMailSender.createMimeMessage();
		try{
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
			
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text,true);
			
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
			
			mailStatus = true;
		}
		 catch (MessagingException e) {
			logger.info(e.getMessage(),e);
			throw new CommonException(e.getMessage());
		}
		
		
		return mailStatus;
	}
}
