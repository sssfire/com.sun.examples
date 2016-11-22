package com.sun.example.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 1. count++�����̰߳�ȫ�ģ���˶��̲߳���ʱ��Ȼ����ͬ������
 * 2. ʹ��AtomicInteger���̰߳�ȫ�ģ����԰�ȫ�Ľ��в�������
 * 3. Demo����ʾ10���߳�ÿ���̵߳���100000�Ľ��������AtomicInteger���ۼӽ������ȷ�ģ���count++�ǲ���ȷ�ġ�
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
