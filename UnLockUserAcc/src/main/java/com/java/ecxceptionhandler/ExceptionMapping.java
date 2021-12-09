package com.java.ecxceptionhandler;


import java.io.IOException;
import java.time.LocalDate;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.java.binding.ExceptionMessage;
import com.java.customexception.CommonException;

@RestControllerAdvice
public class ExceptionMapping {
	
	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<ExceptionMessage> fileException()
	{
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		
		exceptionMessage.setErroCode("F102");
		exceptionMessage.setErrorMessage("File loading issue");
		exceptionMessage.setDateAndTime(LocalDate.now());
		
		return new ResponseEntity<>(exceptionMessage,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = CommonException.class)
	public ResponseEntity<ExceptionMessage> commonException()
	{
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		
		exceptionMessage.setErroCode("F103");
		exceptionMessage.setErrorMessage("Issue at server level please try later");
		exceptionMessage.setDateAndTime(LocalDate.now());
		
		return new ResponseEntity<>(exceptionMessage,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
