package com.sun.spring.example.beans.basic;

import java.util.List;

public class Person {
	
	private Car car;
	private String name;
	private List<Address> addresses;
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	public Car getCar() {
		return car;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Person [car=" + car + ", name=" + name + ", addresses=" + addresses + "]";
	}

	
}
