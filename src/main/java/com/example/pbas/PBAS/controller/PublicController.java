package com.example.pbas.PBAS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

	@RequestMapping( value = "/hello" ,method = RequestMethod.POST)
	public String publicPage() {
		return "In Public controller";
	}

}