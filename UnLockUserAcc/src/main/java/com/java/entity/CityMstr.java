package com.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "CITY_MSTR")
public class CityMstr {

	@Id
	@Column(name = "CITY_ID")
	private Long cityId;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "CITY_NAME")
	private String cityName;

	public CityMstr() {
		super();
	}

	public CityMstr(Long cityId, Long stateId, String cityName) {
		super();
		this.cityId = cityId;
		this.stateId = stateId;
		this.cityName = cityName;
	}



	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
