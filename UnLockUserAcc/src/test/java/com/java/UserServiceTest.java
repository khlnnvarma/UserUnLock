package com.java;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import com.java.binding.CountriesList;
import com.java.binding.LoginForm;
import com.java.binding.UnlockAccountForm;
import com.java.binding.UserRegForm;
import com.java.constants.AppConstants;
import com.java.customexception.CommonException;
import com.java.entity.CityMstr;
import com.java.entity.CountryMstr;
import com.java.entity.StateMstr;
import com.java.entity.UserDetails;
import com.java.props.AppProperties;
import com.java.repository.CityMstrRepo;
import com.java.repository.CountryMstrRepo;
import com.java.repository.StateMstrRepo;
import com.java.repository.UserDetailsRepo;
import com.java.services.imp.UserServices;
import com.java.util.EmailUtils;


@WebMvcTest(value = UserServices.class)
public class UserServiceTest {
	

	@MockBean
	private UserDetailsRepo userRepo;

	@MockBean
	private CountryMstrRepo countryMstrRepo;
	
	@MockBean
	private StateMstrRepo stateMstrRepo;
	
	@MockBean
	private CityMstrRepo cityMstrRepo;
		
	@MockBean
	private EmailUtils emailUtils;
	
	@MockBean
	private AppProperties appProperties;
	
	

	@Test
	public void loginCheckTest() throws CommonException {
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setUserId(1L);
		userDetails.setFirstName("khlnn");
		userDetails.setLastName("varma");
		
		when(userRepo.findByEmailIdAndUserpwd("varma", "cQxSbArd1dlCH510ChspYA==")).thenReturn(userDetails);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		
		LoginForm loginForm= new LoginForm();
		
		loginForm.setUserId("varma");
		loginForm.setUserPwd("varma");
		
		String status = userServices.loginCheck(loginForm);
		
		assertEquals(status, AppConstants.SUCCESS);
		
	}
	
	@Test
	public void loginCheckAccLockedTest() throws CommonException {
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setUserId(1L);
		userDetails.setFirstName("khlnn");
		userDetails.setLastName("varma");
		userDetails.setAccStatus("Locked");
		
		Map<String, String> messages = new TreeMap<>();
		
		messages.put("accLocked", "Account Is Locked");
		
		when(userRepo.findByEmailIdAndUserpwd("varma", "cQxSbArd1dlCH510ChspYA==")).thenReturn(userDetails);
		when(appProperties.getMessages()).thenReturn(messages);
		
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		userServices.setAppProperties(appProperties);
		
		LoginForm loginForm= new LoginForm();
		
		loginForm.setUserId("varma");
		loginForm.setUserPwd("varma");
		
		String status = userServices.loginCheck(loginForm);
		
		assertEquals(status, "Account Is Locked");
	
	}
	
	@Test
	public void loginCheckInvalidCredentialsTest() throws CommonException {
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setUserId(1L);
		userDetails.setFirstName("khlnn");
		userDetails.setLastName("varma");
		
		Map<String, String> messages = new TreeMap<>();
		
		messages.put("invalidCredentials", "Invalid Credentials");
		
		when(userRepo.findByEmailIdAndUserpwd("varma", "cQxSbArd1dlCH510ChspYA=")).thenReturn(userDetails);
		when(appProperties.getMessages()).thenReturn(messages);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		userServices.setAppProperties(appProperties);
		
		LoginForm loginForm= new LoginForm();
		
		loginForm.setUserId("varma");
		loginForm.setUserPwd("varma");
		
		String status = userServices.loginCheck(loginForm);
		
		assertEquals(status, "Invalid Credentials");

	}
	
	
	@Test
	public void getCountriesTest() throws Exception
	{
		CountriesList countriesList = new CountriesList();
		
		List<CountryMstr> l1 = new ArrayList();
		l1.add(new CountryMstr(1L,"India"));
		l1.add(new CountryMstr(1L,"Nepal"));
		l1.add(new CountryMstr(1L,"Aus"));
			
		
		when(countryMstrRepo.findAll()).thenReturn(l1);
		
		UserServices userServices = new UserServices();
		userServices.setCountryMstrRepo(countryMstrRepo);
		
		CountriesList countriesList2 = userServices.getCountries();
		
		assertInstanceOf(CountriesList.class, countriesList2);
		
				
	}
	
	@Test
	public void getStateTest() throws Exception
	{
		List<StateMstr> l2 = new ArrayList();
		l2.add(new StateMstr(1L,1L,"A.p"));
		l2.add(new StateMstr(2L,1L,"U.p"));
		l2.add(new StateMstr(3L,1L,"TN"));
				
		when(stateMstrRepo.findByCountryId(1L)).thenReturn(l2);
		
		UserServices userServices = new UserServices();
		userServices.setStateMstrRepo(stateMstrRepo);
		
		Map<Long, String> stateMap2 = userServices.getState(1L);
		
		assertEquals(3, stateMap2.size());
		
				
	}

	@Test
	public void getCityTest() throws Exception
	{
		List<CityMstr> l3 = new ArrayList();
		l3.add(new CityMstr(1L,1L,"HYD"));

		
		when(cityMstrRepo.findByStateId(1L)).thenReturn(l3);
		
		UserServices userServices = new UserServices();
		userServices.setCityMstrRepo(cityMstrRepo);
		
		Map<Long, String> cityMap2 = userServices.getCity(1L);
		
		assertEquals(1, cityMap2.size());
			
	}
	
	@Test
	public void checkEmailTest() throws Exception
	{
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmailId("varma@gmail.com");
		
		Optional<UserDetails> userOptional = Optional.of(new UserDetails()) ;
		
		when(userRepo.findOne(Mockito.any(Example.class))).thenReturn(userOptional);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		
		Boolean status=userServices.emailCheck("varma@gmail.com");
		
		assertTrue(status);
		
	}
	
	@Test
	public void checkErrorEmailTest() throws Exception
	{
		Optional<UserDetails> userOptional = Optional.ofNullable(null);
		
		when(userRepo.findOne(Mockito.any(Example.class))).thenReturn(userOptional);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		
		Boolean status=userServices.emailCheck("varma");
		
		assertFalse(status);
		
	}
	
	
	@Test
	public void unlockAccountTest() throws Exception
	{
		
		UnlockAccountForm unlockAccountForm= new UnlockAccountForm();
		
		unlockAccountForm.setEmailId("varma");
		unlockAccountForm.setNewPassword("varma");
		unlockAccountForm.setTempPassword("varma");
		
		
		String emailId = unlockAccountForm.getEmailId();
		String tempPassword = unlockAccountForm.getTempPassword();
		
		UserDetails userDetails = new UserDetails();
		userDetails.setEmailId("varma");
		
		when(userRepo.findByEmailIdAndUserpwd(emailId, tempPassword)).thenReturn(userDetails);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		
		Boolean status=userServices.unlockAccount(unlockAccountForm);
		
		assertTrue(status);
		
	}	
	
	@Test
	public void unlockErrorAccountTest() throws Exception
	{
		
		UnlockAccountForm unlockAccountForm= new UnlockAccountForm();
		
		unlockAccountForm.setEmailId("varma");
		unlockAccountForm.setNewPassword("varma");
		unlockAccountForm.setTempPassword("varma");
		
		
		String emailId = unlockAccountForm.getEmailId();
		String tempPassword = "novarma";
		
		UserDetails userDetails = new UserDetails();
		userDetails.setEmailId("varma");
		
		when(userRepo.findByEmailIdAndUserpwd(emailId, tempPassword)).thenReturn(userDetails);
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		
		Boolean status=userServices.unlockAccount(unlockAccountForm);
		
		assertFalse(status);
		
	}	
	
	

	@Test
	public void saveDetailsTest() throws CommonException,Exception
	{
		
		
		UserRegForm userRegForm= new UserRegForm();
		userRegForm.setEmailId("hlnnvarmak@gmail.com");
		userRegForm.setFirstName("varma");
		userRegForm.setLastName("varma");
		
		UserDetails userDetails = new UserDetails();
		BeanUtils.copyProperties(userRegForm, userDetails);
		userDetails.setAccStatus("Locked");
		
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setEmailId("hlnnvarmak@gmail.com");
		userDetails1.setFirstName("varma");
		userDetails1.setLastName("varma");
		userDetails1.setUserpwd("varma");
		userDetails1.setUserId(1L);
		
		
		Map<String, String> messages = new TreeMap<>();
		
		messages.put("unlockMailSubject", "Unlock IES Account");
		messages.put("emailtemplate", "src/main/resources/templates/UnlockMailTemp");
		
		when(appProperties.getMessages()).thenReturn(messages);
		when(userRepo.save(Mockito.any(UserDetails.class))).thenReturn(userDetails1);
		
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		userServices.setAppProperties(appProperties);
		userServices.setEmailUtils(emailUtils);
		
		Boolean status=userServices.saveUsers(userRegForm);
		
		assertTrue(status);
		
			
	}
	
	
	@Test
	public void saveErrorDetailsTest() throws CommonException,Exception
	{
		
		
		UserRegForm userRegForm= new UserRegForm();
		userRegForm.setEmailId("hlnnvarmak@gmail.com");
		userRegForm.setFirstName("varma");
		userRegForm.setLastName("varma");
		
		UserDetails userDetails = new UserDetails();
		BeanUtils.copyProperties(userRegForm, userDetails);
		userDetails.setAccStatus("Locked");
		
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setEmailId("hlnnvarmak@gmail.com");
		userDetails1.setFirstName("varma");
		userDetails1.setLastName("varma");
		userDetails1.setUserpwd("varma");
		
		
		Map<String, String> messages = new TreeMap<>();
		
		messages.put("unlockMailSubject", "Unlock IES Account");
		messages.put("emailtemplate", "src/main/resources/templates/UnlockMailTemp");
		
		when(appProperties.getMessages()).thenReturn(messages);
		when(userRepo.save(Mockito.any(UserDetails.class))).thenReturn(userDetails1);
		
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		userServices.setAppProperties(appProperties);
		userServices.setEmailUtils(emailUtils);
		
		Boolean status=userServices.saveUsers(userRegForm);
		
		assertFalse(status);
		
			
	}
	
	
	@Test
	public void saveExceptionDetailsTest() throws CommonException,Exception
	{
		
		
		UserRegForm userRegForm= new UserRegForm();
		Boolean status=true;
		
		UserDetails userDetails = new UserDetails();
		BeanUtils.copyProperties(userRegForm, userDetails);
		userDetails.setAccStatus("Locked");
		
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setEmailId("hlnnvarmak@gmail.com");
		userDetails1.setFirstName("varma");
		
		userDetails1.setUserpwd("varma");
		
		
		Map<String, String> messages = new TreeMap<>();
		
		messages.put("unlockMailSubject", "Unlock IES Account");
		messages.put("emailtemplate", "src/main/resources/templates/UnlockMailTem");
		
		when(appProperties.getMessages()).thenReturn(messages);
		when(userRepo.save(Mockito.any(UserDetails.class))).thenReturn(userDetails1);
		
		
		UserServices userServices = new UserServices();
		userServices.setUserRepo(userRepo);
		userServices.setAppProperties(appProperties);
		userServices.setEmailUtils(emailUtils);
	
		try {
		 status=userServices.saveUsers(userRegForm);
		}
		catch (Exception e) {
			assertTrue(e instanceof CommonException);
		}
		
			
	}
	
	
	
	@Test
	public void forgetpasswordTest()
	{
		
		Optional<UserDetails> userDetailsOptional = Optional.of(new UserDetails());
		
		when(userRepo.findOne(Mockito.any(Example.class))).thenReturn(userDetailsOptional);
		
		UserServices userServices = new UserServices();
		
		userServices.setUserRepo(userRepo);
		
		Boolean status = userServices.forgetpassword("khlnnvarma1@gmail.com");
		
		assertTrue(status);
		
	}
	
	
	@Test
	public void forgetpasswordErrorTest()
	{
		
		Optional<UserDetails> userDetailsOptional = Optional.ofNullable(null);
		
		when(userRepo.findOne(Mockito.any(Example.class))).thenReturn(userDetailsOptional);
		
		UserServices userServices = new UserServices();
		
		userServices.setUserRepo(userRepo);
		
		Boolean status = userServices.forgetpassword("khlnnvarma1@gmail.com");
		
		assertFalse(status);
		
	}

	
	
}