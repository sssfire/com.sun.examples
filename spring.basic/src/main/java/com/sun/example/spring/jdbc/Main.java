package com.sun.example.spring.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-jdbc.xml");
		
		CountryDao countryDao = (CountryDao) ctx.getBean("countryDao");
		Country country = countryDao.getCity(23);
		System.out.println(country);
		
	}
	
}
