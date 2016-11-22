package com.sun.example.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ArithmeticCalculatorLoggingProxy{
	
	//Ҫ����Ķ���
	private ArithmeticCalculator target;
	
	public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
		super();
		this.target = target;
	}
	
	//���صĴ������
	public ArithmeticCalculator getLoggingProxy() {
		ArithmeticCalculator proxy = null;
		
		ClassLoader loader = target.getClass().getClassLoader();
		Class[] interfaces = new Class[]{ArithmeticCalculator.class};
		InvocationHandler h = new InvocationHandler() {
			
			/**
			 * proxy: �������һ�㲻�ø÷���
			 * method: ���ڱ����õķ���
			 * args: ���÷�������Ĳ���
			 */
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				//����Ŀ�귽��
				Object result = null;
				
				try {
					//�����ڴ�λ�����ӡ�ǰ��֪ͨ��
					//ǰ��֪ͨ����ӡ��־
					String methodName = method.getName();
					System.out.println("[before]The method " + methodName + " begin with " + Arrays.asList(args));

					//������ִ��
					result = method.invoke(target, args);
					
					//����֪ͨ: ���Է��ʵ������ķ���ֵ
				}catch(NullPointerException e){
					e.printStackTrace();
					//�쳣֪ͨ: ���Է��ʵ��������ֵ��쳣
				}
				
				//�����ڴ�λ�����ӡ�����֪ͨ������Ϊ�������ܻ�����쳣�����Է��ʲ��������ķ���ֵ
				//����֪ͨ����ӡ��־
				System.out.println("[after]The method ends with " + result);
				
				return result;
			}
		};
		
		proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);
		
		return proxy;
	}

}
