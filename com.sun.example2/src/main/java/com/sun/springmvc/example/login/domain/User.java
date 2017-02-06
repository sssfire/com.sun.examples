package com.sun.springmvc.example.login.domain;

import java.util.concurrent.ConcurrentHashMap;

public class User {
	private String userName;
	private String password;
	private String realName;
	private ConcurrentHashMap<String, Profile> profilehm = new ConcurrentHashMap<String, Profile>();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public void setProfile(Profile profile){
		if(profile!=null){
			profilehm.put(profile.getProfileName(), profile);
		}
	}
	
	public Profile getProfile(String profileName){
		return profilehm.get(profileName);
	}
}
