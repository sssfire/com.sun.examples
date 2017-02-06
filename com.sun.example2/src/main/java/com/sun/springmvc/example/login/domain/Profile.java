package com.sun.springmvc.example.login.domain;

public class Profile {
	private String profileName;
	private String userGroup;
	private String accesslevel;
	private String loginArea;
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getAccesslevel() {
		return accesslevel;
	}
	public void setAccesslevel(String accesslevel) {
		this.accesslevel = accesslevel;
	}
	public String getLoginArea() {
		return loginArea;
	}
	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}
}
