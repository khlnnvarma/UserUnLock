package com.java.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "UserdetailSeq")
	@SequenceGenerator(name = "UserdetailSeq",initialValue = 1,sequenceName = "USERDETAILSEQ",allocationSize = 1)
	private Long userId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="DOB")
	private String dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="PHONE_NO")
	private Long phNo;
	
	@Column(name="COUNTRY_ID")
	private Long countryId;
	
	@Column(name="STATE_ID")
	private Long stateId;
	
	@Column(name="CITY_ID")
	private Long cityId;
	
	@Column(name="USER_PWD")
	private String userpwd;
	
	@Column(name = "ACC_STATUS")
	private String accStatus;
	
	@CreationTimestamp
	@Column(name="CREATED_ON",updatable = false)
	private LocalDate createdOn;
	
	@UpdateTimestamp
	@Column(name="UPDATED_ON",insertable = false)
	private LocalDate updatedOn;

	public UserDetails() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getPhNo() {
		return phNo;
	}

	public void setPhNo(Long phNo) {
		this.phNo = phNo;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDate getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
	
	
	
	
	
	
	
	
	
}
