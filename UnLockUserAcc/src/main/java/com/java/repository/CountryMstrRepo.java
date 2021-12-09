package com.java.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.CountryMstr;

public interface CountryMstrRepo extends JpaRepository<CountryMstr, Serializable> {
	
	

}
