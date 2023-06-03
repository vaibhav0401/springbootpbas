package com.example.pbas.PBAS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.pbas.PBAS.entity.Role;
import com.example.pbas.PBAS.entity.User;
import com.example.pbas.PBAS.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username)
								  .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		List<GrantedAuthority> authorities = user.getRoles()
												 .stream()
												 .map(role -> new SimpleGrantedAuthority(role.getName()))
												 .collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities
		);
	}
}