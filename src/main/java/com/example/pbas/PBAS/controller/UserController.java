package com.example.pbas.PBAS.controller;

import com.example.pbas.PBAS.dto.UserLoginDto;
import com.example.pbas.PBAS.dto.UserRegistrationDto;
import com.example.pbas.PBAS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api")
public class UserController {


	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
		var response = userService.registerUser(registrationDto);
		return  ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
		var response = userService.loginUser(loginDto);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/public")
	@PermitAll
	public String publicApi() {
		return "Public API accessed";
	}

	@GetMapping("/private/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String privateUserApi() {
		return "Private User API accessed";
	}


	@GetMapping("/private/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String privateAdminApi() {
		return "Private Admin API accessed";
	}

}