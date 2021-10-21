package com.spring.wealthManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.wealthManagement.dao.UserRepository;
import com.spring.wealthManagement.models.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		System.out.println("User:"+ user.toString());
		
		if(user == null)
			System.out.println("Not found");
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}
}
