package com.sun.example.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ArithmeticCalculatorLoggingProxy{
	
	//要代理的对象
	private ArithmeticCalculator target;
	
	public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
		super();
		this.target = target;
	}
	
	//返回的代理对象
	public ArithmeticCalculator getLoggingProxy() {
		ArithmeticCalculator proxy = null;
		
		ClassLoader loader = target.getClass().getClassLoader();
		Class[] interfaces = new Class[]{ArithmeticCalculator.class};
		InvocationHandler h = new InvocationHandler() {
			
			/**
			 * proxy: 代理对象。一般不用该方法
			 * method: 正在被调用的方法
			 * args: 调用方法传入的参数
			 */
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				//调用目标方法
				Object result = null;
				
				try {
					//可以在此位置增加“前置通知”
					//前置通知：打印日志
					String methodName = method.getName();
					System.out.println("[before]The method " + methodName + " begin with " + Arrays.asList(args));

					//方法的执行
					result = method.invoke(target, args);
					
					//返回通知: 可以访问到方法的返回值
				}catch(NullPointerException e){
					e.printStackTrace();
					//异常通知: 可以访问到方法出现的异常
				}
				
				//可以在此位置增加“后置通知”，因为方法可能会出现异常，所以访问不到方法的返回值
				//后置通知：打印日志
				System.out.println("[after]The method ends with " + result);
				
				return result;
			}
		};
		
		proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);
		
		return proxy;
	}

}
