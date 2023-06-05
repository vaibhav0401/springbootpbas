package com.example.pbas.PBAS.service;

import com.example.pbas.PBAS.dto.Response;
import com.example.pbas.PBAS.dto.UserLoginDto;
import com.example.pbas.PBAS.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	UserDetails loadUserByUsername(String username);
	Response registerUser(UserRegistrationDto userRegistrationDto) ;
	Response loginUser(UserLoginDto userLoginDto);
}