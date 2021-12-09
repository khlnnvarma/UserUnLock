package com.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "STATE_MSTR")
public class StateMstr {
	
	@Id
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "COUNTRY_ID")
	private Long countryId;
	
	@Column(name = "STATE_NAME")
	private String stateName;

	public StateMstr() {
		super();
	}



	public StateMstr(Long stateId, Long countryId, String stateName) {
		super();
		this.stateId = stateId;
		this.countryId = countryId;
		this.stateName = stateName;
	}



	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
	
	

}
