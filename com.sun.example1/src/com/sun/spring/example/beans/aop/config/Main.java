package com.sun.spring.example.beans.aop.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		//1. ��ȡIOC����
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-aop-config.xml");
		
		//2. ��ȡbeanʵ��
		ArithmeticCalculator arithmeticCalculator = ctx.getBean(ArithmeticCalculator.class);
		//ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorImpl");
		
		//3. ����beanʵ������
		int result = arithmeticCalculator.add(5, 6);
		System.out.println("Result: " + result);
		
		result = arithmeticCalculator.div(6, 3);
		System.out.println("Result: " + result);
		
		//4. �쳣ʵ������֤�Ƿ����ú���֪ͨ
//		result = arithmeticCalculator.div(100, 0);
//		System.out.println("Result: " + result);
		
		double result1 = arithmeticCalculator.sub(30, 20);
		System.out.println("Result: " + result1);
	}
	
}
