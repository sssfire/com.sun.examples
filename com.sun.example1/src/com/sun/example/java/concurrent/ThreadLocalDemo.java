package com.sun.example.java.concurrent;

public class ThreadLocalDemo {
	
	private final ThreadLocal<Integer> value = new ThreadLocal<Integer>();
	
	public static void main(String[] args){
		ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
		for(int i=0;i<100;i++){
			new Thread(threadLocalDemo.new MyThread()).start();
		}
	}
	
	public void calculate(Integer n){
		value.set(0);
		System.out.println("Thread: "+ Thread.currentThread() + " init value: " + value.get());
		for(int i=0;i<n;i++){
			value.set( value.get() + i );
		}
		System.out.println("Thread: "+ Thread.currentThread() + " value: " + value.get());
	}
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			calculate(10000);			
		}
		
	}
	
}
