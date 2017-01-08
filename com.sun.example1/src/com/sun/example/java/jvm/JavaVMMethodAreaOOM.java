package com.sun.example.java.jvm;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 验证方法区溢出异常
 * 1. 使用CGLib库不断创建新的类，从而使方法区超出容量限制产生溢出
 * 2. 方法区会保存类名，访问修饰符，常量池，字段描述，方法描述等
 * 3. 虚拟机参数如下：
 * -XX:PermSize=10M -XX:MaxPermSize=10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * 4. 通过dump文件可以发现方法区溢出异常
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
