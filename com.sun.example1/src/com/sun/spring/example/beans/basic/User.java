package com.sun.spring.example.beans.basic;

public class User {
	private String name;
	private String realName;
	private Person person;
	private int level;
	private String description;
	
	void initUser(){
		System.out.println(name + " initialize...");
	}

	void destroyUser(){
		System.out.println(name + " destroy...");
	}
	
	public User() {
		System.out.println("User constructor...");
	}
	
	public void setName(String name) {
		System.out.println("Set name: " + name);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", realName=" + realName + ", person=" + person + ", level=" + level
				+ ", description=" + description + "]";
	}
	
}
