package com.java.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.binding.CountriesList;
import com.java.binding.LoginForm;
import com.java.binding.UnlockAccountForm;
import com.java.binding.UserRegForm;
import com.java.constants.AppConstants;
import com.java.customexception.CommonException;
import com.java.props.AppProperties;
import com.java.services.UserDetailsService;


@RestController
@RequestMapping("/User/Reg")
public class UserRegController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AppProperties appProperties;
	
	@PostMapping(value = "login/status",produces = {"application/json"})
	public ResponseEntity<String> userLoginCheck(@RequestBody LoginForm loginForm)throws CommonException
	{
		 String loginStatus = userDetailsService.loginCheck(loginForm);
		 
		 if(AppConstants.SUCCESS.equals(loginStatus))
		 {
			 return new ResponseEntity<>(appProperties.getMessages().get(loginStatus), HttpStatus.ACCEPTED);
		 }
		 else {
			 return new ResponseEntity<>(loginStatus, HttpStatus.BAD_REQUEST);
		}
			 
	}
	
	@GetMapping(value = "email/check/{emailId}",produces  = {"application/json"})
	public ResponseEntity<String> checkEmail(@PathVariable String emailId)
	{
		Boolean status = userDetailsService.emailCheck(emailId);
		 if(status)
		 {
			 return new ResponseEntity<>(AppConstants.NOTUNIQUE, HttpStatus.NOT_FOUND);
		 }
		 else {
			 return new ResponseEntity<>(AppConstants.UNIQUE, HttpStatus.FOUND);
		}
	}
	
	
	@GetMapping(value = "Get/Countries",produces = {"application/json"})
	public ResponseEntity<CountriesList> getCountries()
	{
		CountriesList  countries = userDetailsService.getCountries();
		
		
		return new ResponseEntity<>(countries, HttpStatus.FOUND);
		
	}
	
	@GetMapping(value = "Get/States/{countryid}",produces = {"application/json"})
	public ResponseEntity<? extends Object> getStates(@PathVariable Long countryid)
	{
		Map<Long, String>  states = userDetailsService.getState(countryid);
		
		if(!states.isEmpty())
			return new ResponseEntity<>(states, HttpStatus.FOUND);
		else 
			return new ResponseEntity<>(appProperties.getMessages().get(AppConstants.STATENOTFOUND), HttpStatus.NOT_FOUND);
		
	}
	
	
	@GetMapping(value = "Get/City/{stateid}",produces = {"application/json"})
	public ResponseEntity<? extends Object> getCity(@PathVariable Long stateid)
	{
		Map<Long, String>  city = userDetailsService.getCity(stateid);
		
		if(!city.isEmpty())
			return new ResponseEntity<>(city, HttpStatus.FOUND);
		else 
			return new ResponseEntity<>(appProperties.getMessages().get(AppConstants.CITYNOTFOUND), HttpStatus.NOT_FOUND);
		
	}
	
	
	@PostMapping(value = "unlock/account",produces  = {"application/json"})
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm unlockAccount)throws CommonException
	{
		Boolean status = userDetailsService.unlockAccount(unlockAccount);
		 if(status)
		 {
			 return new ResponseEntity<>(AppConstants.UNLOCKED, HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(AppConstants.WRONGCREDENTIALS, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "savedetails",consumes = {"application/xml", "application/json"},produces = {"application/xml", "application/json"} )
	public ResponseEntity<String> saveDetails(@RequestBody UserRegForm userRegForm)throws CommonException
	{
		if(userDetailsService.saveUsers(userRegForm))
		{
			return new ResponseEntity<>(appProperties.getMessages().get(AppConstants.REGSUCCESS), HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(appProperties.getMessages().get(AppConstants.REGFAILED), HttpStatus.BAD_REQUEST);
		}
	}

}
