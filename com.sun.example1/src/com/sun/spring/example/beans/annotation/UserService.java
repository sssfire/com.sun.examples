package com.sun.spring.example.beans.annotation;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	public void add(){
		System.out.println("UserService add...");
	}
	
}
