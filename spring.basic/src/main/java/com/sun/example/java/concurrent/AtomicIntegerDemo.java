package com.sun.example.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 1. count++不是线程安全的，因此多线程并发时仍然会有同步问题
 * 2. 使用AtomicInteger是线程安全的，可以安全的进行并发访问
 * 3. Demo中显示10个线程每个线程递增100000的结果，其中AtomicInteger的累加结果是正确的，但count++是不正确的。
 *
 */
public class AtomicIntegerDemo {

	private int count = 10;
	
	private SafeSeq safeSeq = new SafeSeq();
	private NonSafeSeq nonSafeSeq = new NonSafeSeq();
	
	private CountDownLatch cdl = new CountDownLatch(count);
	
	class SafeSeq{
		AtomicInteger i = new AtomicInteger(0);
		
		public void increase(){
			i.incrementAndGet();
		}
		
		public int get(){
			return i.get();
		} 
	}
	
	class NonSafeSeq{
		int i = 0;
		
		public void increase(){
			i++;
		}
		
		public int get(){
			return i;
		} 
	}
	
	class CountThread implements Runnable{
		
		@Override
		public void run() {
			
			for (int i = 0; i < 100000; i++) {
				safeSeq.increase();
				nonSafeSeq.increase();
			}
			
			System.out.println(Thread.currentThread().getName() +" is finished");
			
			cdl.countDown();
		}
		
	}
	
	public void testCountDown(AtomicIntegerDemo demo) throws InterruptedException{
		for (int i = 0; i < count; i++) {
			new Thread(demo.new CountThread()).start();
		}
		
		cdl.await();
		
		System.out.println("Thread[" + Thread.currentThread().getName() + "] - SafeSeq: " + safeSeq.get());
		System.out.println("Thread[" + Thread.currentThread().getName() + "] - NonSafeSeq: " + nonSafeSeq.get());
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		AtomicIntegerDemo demo = new AtomicIntegerDemo();
		demo.testCountDown(demo);
	}

}
