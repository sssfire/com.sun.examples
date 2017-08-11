package com.sun.example.spring.annotation.generic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
		
		UserService userService = (UserService) ctx.getBean("userService1");
		userService.add(new User());
		
		RoleService roleService = (RoleService) ctx.getBean("roleService");
		roleService.add(new Role());
		
	}
	
	
}
