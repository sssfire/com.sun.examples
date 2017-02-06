package com.sun.springmvc.example.login.service;

import org.springframework.stereotype.Service;

import com.sun.springmvc.example.login.domain.Profile;
import com.sun.springmvc.example.login.domain.User;

@Service
public class UserService {
	
	public void createUser(User user){
		//create user...
	}

	public User getUserById(String userId) {
		User user = new User();
		if("test".equals(userId)){
			user.setUserName("Test");
			user.setRealName("Test User");
			user.setPassword("Test123");
		}else{
			user.setUserName(userId);
			user.setRealName(userId);
			user.setPassword(userId);
		}
		return user;
	}

	public Profile getProfileByUserId(String userId, String profileName) {
		Profile profile = new Profile();
		if("test".equals(userId)&&"testProfile".equals(profileName)){
			profile.setProfileName(profileName);
			profile.setUserGroup("super group");
			profile.setAccesslevel("999");
			profile.setLoginArea("All Area");
		}else{
			profile.setProfileName(profileName);
			profile.setUserGroup("test group");
			profile.setAccesslevel("001");
			profile.setLoginArea("Demo Area");
		}
		return profile;
	}
	
}
