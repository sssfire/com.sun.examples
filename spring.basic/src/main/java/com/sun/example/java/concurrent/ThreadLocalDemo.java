package com.sun.example.java.concurrent;

/**
 * ThreadLocal实现原理
 * 1. 每个Thread都有一个本地变量threadLocals, 类型是ThreadLocal.ThreadLocalMap，用于存放和这个Thread相关的专属对象/值
 * 2. 在主线程中申请了ThreadLocal类型的实例变量tl后，在子线程thread-n中引用ThreadLocal的实例变量tl进行set操作，在set方法中
 *    会引用子线程thread-n对应的threadLocals(即ThreadLocalMap)，把主线程中的value作为key，需要设置的值作为value加入Map中。
 * 3. 如果一个子线程引用多个tl类型的实例变量，则在该子线程的threadLocals中会有多个entry，分别以这些实例变量为key进行存储。
 * 4. 因此主线程中的tl并不存储实际的变量，而只是变量设置的工具。
 * @author I068353
 *
 */

public class ThreadLocalDemo {
	
	private final ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
	
	public static void main(String[] args){
		ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
		for(int i=0;i<5;i++){
			new Thread(threadLocalDemo.new MyThread()).start();
		}
	}
	
	public void calculate(Integer n){
		tl.set(0);
		System.out.println("Thread: "+ Thread.currentThread() + " init value: " + tl.get());
		for(int i=0;i<n;i++){
			tl.set( tl.get() + i );
		}
		System.out.println("Thread: "+ Thread.currentThread() + " value: " + tl.get());
	}
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			calculate(10000);			
		}
		
		@Override
		public String toString() {
			return "MyThread [id=" + Thread.currentThread().getId() + ", name="
					+ Thread.currentThread().getName() + "]";
		}
	}
	
}
