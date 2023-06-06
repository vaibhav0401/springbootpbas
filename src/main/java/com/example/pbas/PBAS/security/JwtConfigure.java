package com.example.pbas.PBAS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.pbas.PBAS.jwt.JwtAuthenticationFilter;
import com.example.pbas.PBAS.jwt.JwtUtils;

@Configuration
public class JwtConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

@Autowired
private JwtUtils jwtUtils;


	@Override
	public void configure(HttpSecurity http) {
		var tokenFilter = new JwtAuthenticationFilter(jwtUtils);
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}