package com.sanbeso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanbeso.domain.User;
import com.sanbeso.service.UserService;

public class Login {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String doLogin(ModelMap model) {
		System.out.println("Entre aqui");
		
		User user = new User();
		user.setUsername((String)model.get("user"));
		
		User logged = userService.get(user);
		
		model.addAttribute("logged", logged);
		return "hello";
		 
	}
}
