package com.java.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.StateMstr;

public interface StateMstrRepo extends JpaRepository<StateMstr, Serializable>{
	
	public List<StateMstr> findByCountryId(Long countryId);

}
