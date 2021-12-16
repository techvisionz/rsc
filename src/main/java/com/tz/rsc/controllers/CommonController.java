package com.tz.rsc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
	
	@GetMapping("/healthCheck")
	public String healthCheck()
	{
		return "{\"message\":\"ok\"}";
	}
}
