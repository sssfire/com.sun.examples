package com.sun.example.spring.annotation.generic;

public class BaseDao<T> {
	
	public void save(T entity){
		System.out.println("save by " + this);
		System.out.println("save " + entity);
	}
	
}
