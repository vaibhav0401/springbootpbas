package com.example.pbas.PBAS.service;

import com.example.pbas.PBAS.dto.*;
import com.example.pbas.PBAS.entity.Permission;
import com.example.pbas.PBAS.entity.Role;
import com.example.pbas.PBAS.entity.User;
import com.example.pbas.PBAS.jwt.JwtUtils;
import com.example.pbas.PBAS.repository.PermissionRepository;
import com.example.pbas.PBAS.repository.RoleRepository;
import com.example.pbas.PBAS.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtUtils jwtUtils;

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


	@Override
	public UserDetails loadUserByUsername(String username) {
		//Getting user from DB
		var user = userRepository.findByUsername(username)
								  .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		List<GrantedAuthority> authorities = user.getRoles()
												 .stream()
												 .map(role -> new SimpleGrantedAuthority(role.getName()))
												 .collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public Response registerUser(UserRegistrationDto registrationDto) {
		if (userRepository.existsByUsername(registrationDto.getUsername())) {
			logger.error("Username is already taken");
			return new Response("Username is already taken", 500 , "");
		}

		// Create a new user entity
		var user = new User();
		user.setUsername(registrationDto.getUsername());
		user.setPassword(jwtUtils.passwordEncoder().encode(registrationDto.getPassword()));

		if (registrationDto.getRoles() != null) {
			var roles = registrationDto.getRoles()
											  .stream()
											  .map(this::getRole)
											  .collect(Collectors.toList());
			user.setRoles(roles);
		}

		userRepository.save(user);
		logger.info("User {} registered successfully",gson.toJson(user));
		return new Response("User registered successfully", 200 , "");


	}

	private Role getRole(RoleDto roleDto){
		var role = new Role();

		var roleOptional = roleRepository.findByName(roleDto.getName());
		if (roleOptional.isEmpty()) {
			role.setName(roleDto.getName());
			role.setDescription(roleDto.getDescription());
			var permissions = roleDto.getPermissions()
					.stream()
					.map(this::getPermission)
					.collect(Collectors.toSet());
			role.setPermission(permissions);
			roleRepository.save(role);
			return role;
		}
		return roleOptional.get();
	}

	private Permission getPermission(PermissionDto permissionDto){

		var permissionOptional = permissionRepository.findByName(permissionDto.getName());
		var permission = new Permission();
			if (permissionOptional.isEmpty()) {
			permission.setName(permissionDto.getName());
			permission.setDescription(permissionDto.getDescription());
			permissionRepository.save(permission);
			return permission;
		}
		return permissionOptional.get();
	}

	@Override
	public Response loginUser(UserLoginDto loginDto) {

		// Check if the entered user exists

		var userOptional = userRepository.findByUsername(loginDto.getUsername());

		if (userOptional.isEmpty()) {
			logger.error("User not found");
			return new Response("User not found", 400 , "");
		}
		var user = userOptional.get();
		// Check if the provided password matches
		if (!jwtUtils.passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
			logger.error("Invalid password");
			return new Response("Invalid password", 400 , "");
		}

		// Generate a JWT token
		var token = jwtUtils.generateToken(user.getUsername(), user.getRoles());
		logger.info("Logged in successfully");
		return new Response("Logged in successfully", 200 , token);
	}
}