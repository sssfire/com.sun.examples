package com.sun.example.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 演示CountDownLatch的用法
 * CountDownLatch用来在一个主线程中等待其他线程的结束。
 *
 * 前十个线程都执行完毕后，主线程才会执行。
 * 
 */

public class CountDownLatchDemo {

	int count = 10;
	final CountDownLatch countDownLatch = new CountDownLatch(count);
	
	class CountDownThread implements Runnable{

		int index = 0;
		
		public CountDownThread(int index) {
			this.index = index;
		}
		
		@Override
		public void run() {
			
			try {
				Thread.sleep(new Random().nextInt(10)*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread " + index + " has been finished");
			
			countDownLatch.countDown();
		}
		
	}
	
	public void testCountDownThread(){
	
		for (int i = 0; i < count; i++) {
			new Thread(new CountDownThread(i)).start();;
		}
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Now all threads are finished!");
		
	}
	
	public static void main(String[] args) {
		CountDownLatchDemo demo = new CountDownLatchDemo();
		demo.testCountDownThread();
	}
	
}
