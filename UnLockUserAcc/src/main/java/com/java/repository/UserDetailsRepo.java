package com.java.repository;

import java.io.Serializable;


import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Serializable>{

	public UserDetails findByEmailIdAndUserpwd(String userId,String password);
}
