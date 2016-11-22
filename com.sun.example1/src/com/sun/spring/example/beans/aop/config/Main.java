package com.sun.spring.example.beans.aop.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		//1. 获取IOC容器
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-aop-config.xml");
		
		//2. 获取bean实例
		ArithmeticCalculator arithmeticCalculator = ctx.getBean(ArithmeticCalculator.class);
		//ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");
		
		//3. 调用bean实例方法
		int result = arithmeticCalculator.add(5, 6);
		System.out.println("Result: " + result);
		
		result = arithmeticCalculator.div(6, 3);
		System.out.println("Result: " + result);
		
		//4. 异常实例，验证是否会调用后置通知
//		result = arithmeticCalculator.div(100, 0);
//		System.out.println("Result: " + result);
		
		double result1 = arithmeticCalculator.sub(30, 20);
		System.out.println("Result: " + result1);
	}
	
}
