package com.api.webvote.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

	// Classe para apenas testar o status da API
	
	@GetMapping(value = "/api/status")
	public String check()
	{
		return "Online";
	}
}
