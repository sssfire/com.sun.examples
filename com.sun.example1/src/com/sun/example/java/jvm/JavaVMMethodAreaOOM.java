package com.sun.example.java.jvm;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * ��֤����������쳣
 * 1. ʹ��CGLib�ⲻ�ϴ����µ��࣬�Ӷ�ʹ�����������������Ʋ������
 * 2. �������ᱣ���������������η��������أ��ֶ�����������������
 * 3. ������������£�
 * -XX:PermSize=10M -XX:MaxPermSize=10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * 4. ͨ��dump�ļ����Է��ַ���������쳣
 * @author I068353
 *
 */
public class JavaVMMethodAreaOOM {

	private void createClasswithNoneStop(){
		int i=0;
		while(true){
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				
				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(obj, args);
				}
			});
			enhancer.create();
			
			System.out.println(i++);
		}
	}
	
	static class OOMObject{}
	
	public static void main(String[] args) {
		JavaVMMethodAreaOOM oom = new JavaVMMethodAreaOOM();
		oom.createClasswithNoneStop();
	}

}
