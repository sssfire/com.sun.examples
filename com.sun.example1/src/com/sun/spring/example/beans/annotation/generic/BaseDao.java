package com.sun.spring.example.beans.annotation.generic;

public class BaseDao<T> {
	
	public void save(T entity){
		System.out.println("save by " + this);
		System.out.println("save " + entity);
	}
	
}
