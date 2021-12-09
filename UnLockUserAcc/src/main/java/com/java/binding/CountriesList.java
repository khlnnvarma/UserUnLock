package com.java.binding;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountriesList {
	
	private Map<Long, String> countriesMap = new HashMap<>();

	public Map<Long, String> getCountriesMap() {
		return countriesMap;
	}

	public void setCountriesMap(Map<Long, String> countriesMap) {
		this.countriesMap = countriesMap;
	}
	
	
	

}
