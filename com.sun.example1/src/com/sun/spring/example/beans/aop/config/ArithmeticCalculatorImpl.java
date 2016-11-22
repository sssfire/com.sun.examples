package com.sun.spring.example.beans.aop.config;

public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

	@Override
	public int add(int i, int j) {
		int result = i + j;
		return result;
	}

	@Override
	public double sub(int i, int j) {
		int result = i - j;
		return result;
	}

	@Override
	public int mutiple(int i, int j) {
		int result = i * j;
		return result;
	}

	@Override
	public int div(int i, int j) {
		int result = i / j;
		return result;
	}

}
