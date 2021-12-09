package com.java.services.imp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.java.binding.CountriesList;
import com.java.binding.LoginForm;
import com.java.binding.UnlockAccountForm;
import com.java.binding.UserRegForm;
import com.java.constants.AppConstants;
import com.java.customexception.CommonException;
import com.java.util.EncryptionAndDecryption;
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
import com.java.util.EmailUtils;


@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private UserDetailsRepo userRepo;
	
	@Autowired
	private CountryMstrRepo countryMstrRepo;
	
	@Autowired
	private StateMstrRepo stateMstrRepo;
	
	@Autowired
	private CityMstrRepo cityMstrRepo;
	
	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private EmailUtils emailUtils;
	
	
	private Logger log= LoggerFactory.getLogger(UserServices.class);
	
	@Override
	public String loginCheck(LoginForm loginform) throws CommonException {
		
		String passCodes = EncryptionAndDecryption.encrypt(loginform.getUserPwd());
		
		UserDetails userDetails = userRepo.findByEmailIdAndUserpwd(loginform.getUserId(),passCodes);
		
		if(userDetails != null)
		{
			if(AppConstants.LOCKED.equalsIgnoreCase(userDetails.getAccStatus()))
			{
				return appProperties.getMessages().get(AppConstants.ACC_LOCKED);
			}
			else {
				return AppConstants.SUCCESS;
			}
		}
		else {
			return appProperties.getMessages().get(AppConstants.INVALID_CREDENTIALS);
		}
	}

	@Override
	public CountriesList getCountries() {
		
		List<CountryMstr> countryMstr = countryMstrRepo.findAll();
		
		CountriesList countriesList = new CountriesList();
		
		countryMstr.forEach(country -> countriesList.getCountriesMap().put(country.getCountryId(), country.getCountryName()));
		
		
		return countriesList;
	}

	@Override
	public Map<Long, String> getState(Long countryId) {
		
		
		  List<StateMstr> stateMstr = stateMstrRepo.findByCountryId(countryId);
		  
		  Map<Long, String> stateMap = new TreeMap<>();
		  
		  stateMstr.forEach(state -> stateMap.put(state.getStateId(),state.getStateName()));
		 
		
		return stateMap;
	}

	@Override
	public Map<Long, String> getCity(Long stateId) {
		
		 List<CityMstr> cityMstr = cityMstrRepo.findByStateId(stateId);
		  
		  Map<Long, String> cityMap = new TreeMap<>();
		  
		  cityMstr.forEach(city -> cityMap.put(city.getCityId(),city.getCityName()));
		 
		
		return cityMap;
	}

	@Override
	public Boolean emailCheck(String emailId) {
		
		Optional<UserDetails> userEmail = getUserForEmail(emailId);
		
		if(userEmail.isPresent())
		{
			return AppConstants.TRUE;
		}
		else {
			return AppConstants.FALSE;
		}
	}

	@Override
	public Boolean saveUsers(UserRegForm userFormDetails)throws  CommonException {
		
		UserDetails userDetails = new UserDetails();
		
		BeanUtils.copyProperties(userFormDetails, userDetails);
		
		userDetails.setAccStatus("Locked");
		userDetails.setUserpwd(EncryptionAndDecryption.encrypt(getSaltString()));
		
		userDetails = userRepo.save(userDetails);
		
		emailUtils.sendMail(userFormDetails.getEmailId(), appProperties.getMessages().get(AppConstants.UNLOCKMAILSUB) ,frameUnlockMailText(userDetails));
		
		return userDetails.getUserId() != null ? AppConstants.TRUE : AppConstants.FALSE;
		
	}

	@Override
	public Boolean unlockAccount(UnlockAccountForm unlockAccountForm) throws CommonException {
		
		String emailId = unlockAccountForm.getEmailId();
				
		String tempPassword = unlockAccountForm.getTempPassword();
		
		UserDetails userDetails = userRepo.findByEmailIdAndUserpwd(emailId, tempPassword);
		
		if(userDetails != null)
		{
			userDetails.setUserpwd(EncryptionAndDecryption.encrypt(unlockAccountForm.getNewPassword()));
			userDetails.setAccStatus("Unlocked");
			userRepo.save(userDetails);
			return AppConstants.TRUE;
		}
		else {
			return AppConstants.FALSE;
		}
		
	}

	@Override
	public Boolean forgetpassword(String emailId) {
		
		Optional<UserDetails> userEmail = getUserForEmail(emailId);
		
		if(userEmail.isPresent())
		{
			
			return AppConstants.TRUE;
		}
		else {
			return AppConstants.FALSE;
		}
	}

	private Optional<UserDetails> getUserForEmail(String emailId) {
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmailId(emailId);
		
		Example<UserDetails> example =  Example.of(userDetails);
		
		return   userRepo.findOne(example);
		
	}
	
	
	private String getSaltString() {
       
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { 
            int index =(int) (rnd.nextFloat() * AppConstants.SALTCHARS.length());
            salt.append(AppConstants.SALTCHARS.charAt(index));
        }
        return salt.toString();
         

    }
	
	private String frameUnlockMailText(UserDetails userDetails) throws CommonException
	{
		StringBuilder bodyText = new StringBuilder(AppConstants.EMPTYSTRING);
		
		String body = AppConstants.EMPTYSTRING;
		
		
		
		try(FileReader fileReader = new FileReader(appProperties.getMessages().get(AppConstants.EMAILTEMPLATE));
				BufferedReader bufferedReader = new BufferedReader(fileReader)){
			
			String line = bufferedReader.readLine();
			
			while(line != null)
			{
				bodyText.append(line);
				
				line = bufferedReader.readLine();
			}
			
			 body =  bodyText.toString();
			
			 body=	body.replace(AppConstants.FNAME, userDetails.getFirstName());
			 body=	body.replace(AppConstants.LNAME, userDetails.getLastName());
			 body=	body.replace(AppConstants.TEMPPASS, userDetails.getUserpwd());
			 body=	body.replace(AppConstants.EMAILID, userDetails.getEmailId());
			
		} catch (IOException e) {
			log.info(e.getMessage(),e);
			throw new CommonException(e.getMessage());
		}
		
		
		return body;
	}

	public UserDetailsRepo getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserDetailsRepo userRepo) {
		this.userRepo = userRepo;
	}

	public CountryMstrRepo getCountryMstrRepo() {
		return countryMstrRepo;
	}

	public void setCountryMstrRepo(CountryMstrRepo countryMstrRepo) {
		this.countryMstrRepo = countryMstrRepo;
	}

	public StateMstrRepo getStateMstrRepo() {
		return stateMstrRepo;
	}

	public void setStateMstrRepo(StateMstrRepo stateMstrRepo) {
		this.stateMstrRepo = stateMstrRepo;
	}

	public CityMstrRepo getCityMstrRepo() {
		return cityMstrRepo;
	}

	public void setCityMstrRepo(CityMstrRepo cityMstrRepo) {
		this.cityMstrRepo = cityMstrRepo;
	}

	public AppProperties getAppProperties() {
		return appProperties;
	}

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public EmailUtils getEmailUtils() {
		return emailUtils;
	}

	public void setEmailUtils(EmailUtils emailUtils) {
		this.emailUtils = emailUtils;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
