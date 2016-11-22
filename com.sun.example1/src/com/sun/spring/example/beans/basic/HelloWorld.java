package com.sun.spring.example.beans.basic;

public class HelloWorld {
	private String name;
	
	public HelloWorld() {
		System.out.println("HellowWorld Constructor");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void helloWorld(){
		System.out.println("Hello World: " + name);
	}
	
}
