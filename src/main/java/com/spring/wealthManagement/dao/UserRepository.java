package com.spring.wealthManagement.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.wealthManagement.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	//@Query("select u from User u where u.email=: email")
	public User findByEmail(String email);
	//User findByEmail(@Param("email") String email);
}



