package com.sun.example.java.proxy;

public class MainApp {
	
	public static void main(String[] args) {
		ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculatorImpl();
		arithmeticCalculator = new ArithmeticCalculatorLoggingProxy(arithmeticCalculator).getLoggingProxy();
		
		int result = arithmeticCalculator.add(5, 3);
		System.out.println("result:" + result);
		
		result = arithmeticCalculator.sub(6, 2);
		System.out.println("result:" + result);
		
	}
	
}
