package com.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.binding.CountriesList;
import com.java.binding.LoginForm;
import com.java.binding.UnlockAccountForm;
import com.java.binding.UserRegForm;
import com.java.constants.AppConstants;
import com.java.controller.UserRegController;
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
import com.java.services.UserDetailsService;



@WebMvcTest(value = UserRegController.class)
public class UserRegControllerTest {
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private CountryMstrRepo countriesrepo;
	
	@MockBean
	private StateMstrRepo stateMstrRepo;
	
	@MockBean
	private CityMstrRepo cityMstrRepo;
	
	@MockBean
	private AppProperties appProperties;
	
	@MockBean
	private UserDetailsRepo userDetailsRepo;
	
	@Autowired
	private MockMvc mockMvc;

	
	@Test
	public void userLoginCheckTest() throws Exception
	{
		when(userDetailsService.loginCheck(ArgumentMatchers.any())).thenReturn(AppConstants.SUCCESS);
		
		LoginForm loginForm= new LoginForm();
		
		loginForm.setUserId("varma");
		loginForm.setUserPwd("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(loginForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/login/status")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(202, status);
			
	}
	
	
	@Test
	public void userLoginErrorCheckTest() throws Exception
	{
		when(userDetailsService.loginCheck(ArgumentMatchers.any())).thenReturn("false");
		
		LoginForm loginForm= new LoginForm();
		
		loginForm.setUserId("vama");
		loginForm.setUserPwd("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(loginForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/login/status")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(400, status);
		
		
	}
	


	@Test
	public void checkEmailTest() throws Exception
	{
		when(userDetailsService.emailCheck("varma@gmail.com")).thenReturn(true);
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmailId("varma@gmail.com");
		
		Example<UserDetails> example =  Example.of(userDetails);
		
		Optional<UserDetails> userOptional = Optional.of(new UserDetails()) ;
		
		when(userDetailsRepo.findOne(example)).thenReturn(userOptional);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/email/check/varma@gmail.com");
		
		ResultActions performActions = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = performActions.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(404, status);
		
	}
	
	@Test
	public void checkEmailErrorTest() throws Exception
	{
		when(userDetailsService.emailCheck("varma@gmail.co")).thenReturn(true);
		
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmailId("varma@gmail.com");
		
		Example<UserDetails> example =  Example.of(userDetails);
		
		Optional<UserDetails> userOptional = Optional.of(new UserDetails()) ;
		
		when(userDetailsRepo.findOne(example)).thenReturn(userOptional);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/email/check/varma@gmail.com");
		
		ResultActions performActions = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = performActions.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(302, status);
		
	}
	
	
	
	
	
	@Test
	public void getCountriesTest() throws Exception
	{
		CountriesList countriesList = new CountriesList();
		
		List<CountryMstr> l1 = new ArrayList();
		l1.add(new CountryMstr(1L,"India"));
		l1.add(new CountryMstr(1L,"Nepal"));
		l1.add(new CountryMstr(1L,"Aus"));
			
		when(userDetailsService.getCountries()).thenReturn(countriesList);
		
		when(countriesrepo.findAll()).thenReturn(l1);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/Get/Countries");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(302, status);
		
	}
	
	
	@Test
	public void getStateTest() throws Exception
	{
		
		List<StateMstr> l2 = new ArrayList();
		l2.add(new StateMstr(1L,1L,"A.p"));
		l2.add(new StateMstr(2L,1L,"U.p"));
		l2.add(new StateMstr(3L,1L,"TN"));
			
		Map<Long, String> stateMap = new TreeMap<>();
		  
		l2.forEach(state -> stateMap.put(state.getStateId(),state.getStateName()));
		  
		when(userDetailsService.getState(1L)).thenReturn(stateMap);
		
		when(stateMstrRepo.findByCountryId(1L)).thenReturn(l2);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/Get/States/1");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(302, status);
		
	}
	
	@Test
	public void getStateErrorTest() throws Exception
	{
		
		List<StateMstr> l2 = new ArrayList();
		
			
		Map<Long, String> stateMap = new TreeMap<>();
		  
		l2.forEach(state -> stateMap.put(state.getStateId(),state.getStateName()));
		  
		when(userDetailsService.getState(1L)).thenReturn(stateMap);
		
		when(stateMstrRepo.findByCountryId(1L)).thenReturn(l2);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/Get/States/1");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(404, status);
		
	}
	
	@Test
	public void getCityTest() throws Exception
	{
		
		List<CityMstr> l3 = new ArrayList();
		l3.add(new CityMstr(1L,1L,"HYD"));
		l3.add(new CityMstr(1L,2L,"CHENNAI"));
		l3.add(new CityMstr(1L,3L,"BGL"));
			
		Map<Long, String> cityMap = new TreeMap<>();
		  
		 l3.forEach(city -> cityMap.put(city.getCityId(),city.getCityName()));
		  
		when(userDetailsService.getCity(1L)).thenReturn(cityMap);
		
		when(cityMstrRepo.findByStateId(1L)).thenReturn(l3);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/Get/City/1");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(302, status);
		
	}
	
	
	@Test
	public void getErrorCityTest() throws Exception
	{
		
		List<CityMstr> l3 = new ArrayList();
			
		Map<Long, String> cityMap = new TreeMap<>();
		  
		 l3.forEach(city -> cityMap.put(city.getCityId(),city.getCityName()));
		  
		when(userDetailsService.getCity(2L)).thenReturn(cityMap);
		
		when(cityMstrRepo.findByStateId(2L)).thenReturn(l3);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/User/Reg/Get/City/1");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(404, status);
		
	}
	
	
	@Test
	public void unlockAccountTest() throws Exception
	{
		when(userDetailsService.unlockAccount(ArgumentMatchers.any())).thenReturn(AppConstants.TRUE);
		
		UnlockAccountForm unlockAccountForm= new UnlockAccountForm();
		
		unlockAccountForm.setEmailId("varma");
		unlockAccountForm.setNewPassword("varma");
		unlockAccountForm.setTempPassword("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(unlockAccountForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/unlock/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(200, status);
			
	}
	
	
	@Test
	public void unlockErrorAccountTest() throws Exception
	{
		when(userDetailsService.unlockAccount(ArgumentMatchers.any())).thenReturn(AppConstants.FALSE);
		
		UnlockAccountForm unlockAccountForm= new UnlockAccountForm();
		
		unlockAccountForm.setEmailId("varma");
		unlockAccountForm.setNewPassword("varma");
		unlockAccountForm.setTempPassword("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(unlockAccountForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/unlock/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(400, status);
		
		
	}
	
	
	@Test
	public void saveDetailsTest() throws CommonException,Exception
	{
		when(userDetailsService.saveUsers(ArgumentMatchers.any())).thenReturn(AppConstants.TRUE);
		
		UserRegForm userRegForm= new UserRegForm();
		
		userRegForm.setEmailId("varma");
		userRegForm.setFirstName("varma");
		userRegForm.setLastName("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(userRegForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/savedetails")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(200, status);
			
	}
	
	
	@Test
	public void saveDetailsErrorTest() throws CommonException,Exception
	{
		when(userDetailsService.saveUsers(ArgumentMatchers.any())).thenReturn(AppConstants.FALSE);
		
		UserRegForm userRegForm= new UserRegForm();
		
		userRegForm.setEmailId("varma");
		userRegForm.setFirstName("varma");
		userRegForm.setLastName("varma");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String loginDetails = objectMapper.writeValueAsString(userRegForm);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/User/Reg/savedetails")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginDetails);
		

		ResultActions perform = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		
		int status = mockHttpServletResponse.getStatus();
		
		assertEquals(400, status);
		
		
	}
	


}
