package com.java.services;

import java.util.Map;

import com.java.binding.CountriesList;
import com.java.binding.LoginForm;
import com.java.binding.UnlockAccountForm;
import com.java.binding.UserRegForm;
import com.java.customexception.CommonException;

public interface UserDetailsService {
	
	public String loginCheck(LoginForm loginform)throws CommonException;
	
	public CountriesList getCountries();
	
	public Map<Long, String> getState(Long countryId);
	
	public Map<Long, String> getCity(Long stateId);
	
	public Boolean emailCheck(String emailId);
	
	public Boolean saveUsers(UserRegForm userDetails)throws CommonException;
	
	public Boolean unlockAccount(UnlockAccountForm unlockAccountForm)throws CommonException;
	
	public Boolean forgetpassword(String emailId);
 	
	
	

}
