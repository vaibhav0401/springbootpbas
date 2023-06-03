package com.example.pbas.PBAS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

	@RequestMapping( value = "/hello" ,method = RequestMethod.POST)
	public String privatePage(){
		return "In private controller";
	}

}