package com.tz.generate.controller;



import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
public class BaseController {
	// 进入选择页面
	@GetMapping("/")
	public String get() {
		
		return "view";
	}

	@PostMapping
	public void generateCode(HashMap<String, Object> json){
	  
	}
	



}
