package com.sun.example.spring.basic;

import java.util.HashMap;

public class UserFactory {

	public static HashMap<String, User> users = new HashMap<String, User>();

	public static User getUserInstance(String name, String realName, int level, String description) {
		User user = users.get(name);
		
		if (user == null) {
			user = new User();
			user.setName(name);
			user.setRealName(realName);
			user.setLevel(level);
			user.setDescription(description);
			users.put(name, user);
		}
		
		return user;
	}
	
	public User getUserInstance2(String name, String realName, int level, String description) {
		User user = users.get(name);
		
		if (user == null) {
			user = new User();
			user.setName(name);
			user.setRealName(realName);
			user.setLevel(level);
			user.setDescription(description);
			users.put(name, user);
		}
		
		return user;
	}
}
