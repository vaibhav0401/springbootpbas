package com.example.pbas.PBAS.dto;

import com.example.pbas.PBAS.entity.Role;

import java.util.List;

public class UserRegistrationDto {

	private String username;
	private String password;
	private List<RoleDto> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}
}