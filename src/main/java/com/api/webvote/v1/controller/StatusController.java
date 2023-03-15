package com.api.webvote.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/api")
@RestController
public class StatusController {

	// Classe para apenas testar o status da API
	
	@GetMapping(value = "/status")
	public String check()
	{
		return "Online";
	}
}
