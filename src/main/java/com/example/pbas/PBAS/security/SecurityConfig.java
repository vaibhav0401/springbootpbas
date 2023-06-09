package com.example.pbas.PBAS.security;

import com.example.pbas.PBAS.jwt.JwtAuthenticationFilter;
import com.example.pbas.PBAS.jwt.JwtUtils;
import com.example.pbas.PBAS.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserServiceImpl userServiceImpl;

	private static final String[] AUTH_WHITELIST = {
			"/api/register",
			"/api/login"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.antMatchers("/api/public").permitAll()
			.anyRequest().authenticated()// Require authentication
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		var passwordEncoder = jwtUtils.passwordEncoder();
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
	}



}