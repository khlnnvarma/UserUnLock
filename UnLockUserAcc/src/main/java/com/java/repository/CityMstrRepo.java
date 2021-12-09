package com.java.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.CityMstr;

public interface CityMstrRepo extends JpaRepository<CityMstr, Serializable>{
	
	public List<CityMstr> findByStateId(Long stateId);

}
