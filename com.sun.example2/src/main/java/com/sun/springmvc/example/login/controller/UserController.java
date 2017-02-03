package com.sun.springmvc.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sun.springmvc.example.login.domain.User;
import com.sun.springmvc.example.login.service.UserService;

@Controller
@RequestMapping("/login")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	public String register(){
		return "login/register";
	}
	
	@RequestMapping(value="createUser",method=RequestMethod.POST)
	public ModelAndView createUser(User user){
		userService.createUser(user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/createSuccess");
		mav.addObject("user", user);
		return mav;
	}
	
}
