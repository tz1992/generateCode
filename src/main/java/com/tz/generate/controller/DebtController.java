package com.tz.generate.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class DebtController {
	
	@GetMapping("/")
	public String get() {
		
		return "view";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	



}
