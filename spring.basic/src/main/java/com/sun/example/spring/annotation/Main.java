package com.sun.example.spring.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.example.spring.annotation.UserController;
import com.sun.example.spring.annotation.UserRepository;
import com.sun.example.spring.annotation.UserService;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
		
		UserController userController = (UserController) ctx.getBean("userController");
		System.out.println(userController);
		userController.execute();
		
		UserService userService = (UserService) ctx.getBean("userService");
		System.out.println(userService);
		
		UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
		System.out.println(userRepository);
		
		((ClassPathXmlApplicationContext)ctx).close();
	}
	
}
