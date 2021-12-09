package com.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name  = "COUNTRY_MSTR")
public class CountryMstr {
	
	@Id
	@Column(name = "COUNTRY_ID")
	private Long countryId;
	
	@Column(name = "COUNTRY_NAME")
	private String countryName;

	public CountryMstr() {
		super();
	}
	

	public CountryMstr(Long countryId, String countryName) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
	}



	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
	
}
