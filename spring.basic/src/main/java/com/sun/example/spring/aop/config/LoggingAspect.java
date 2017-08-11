package com.sun.example.spring.aop.config;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LoggingAspect {
	
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		
		System.out.println("Before method '" + methodName + "' begin with " + args);
	}
	
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method '" + methodName + "' ends.");
	}
	
	public void afterReturning(JoinPoint joinPoint, Object result){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("AfterReturning method '" + methodName + "' ends with return value: " + result);
	}
	
	public void afterThrowing(JoinPoint joinPoint,  ArithmeticException ex){
//	public void afterThrowing(JoinPoint joinPoint,  Exception ex){
//	public void afterThrowing(JoinPoint joinPoint,  NullPointerException ex){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("afterThrowing method '" + methodName + "' throw exceptions: " + ex.toString());
	}
	
	public Object aroundMethod(ProceedingJoinPoint pjp){
		Object result = null;
		
		String methodName = pjp.getSignature().getName();
		
		try{
			//前置通知
			System.out.println("Aound method ==> The before method '" +methodName+ "' begin with " + Arrays.asList(pjp.getArgs()));
			
			//执行方法
			result = pjp.proceed();
			
			//后置通知
			System.out.println("Aound method ==> The after method '" +methodName+ "' result: " + result);
		}catch(Throwable e){
			//异常通知
			System.out.println("Aound method ==> The after throwing method '" +methodName+ "' exception: " + e.toString());
		}
		
		//返回通知
		System.out.println("Aound method ==> The returning method '" + methodName + "'");
		
		return result;
	}
	
	
}
