package com.sun.spring.example.beans.aop.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

public class ValidationAspect {

	@Before("com.sun.spring.example.beans.aop.LoggingAspect.declearPointcutExpression1()")
	public void validateArgs(JoinPoint joinPoint){
		System.out.println("-->validate:" + Arrays.asList(joinPoint.getArgs()));
	}
	
}
