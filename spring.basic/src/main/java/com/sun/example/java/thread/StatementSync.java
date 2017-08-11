package com.sun.example.java.thread;

import java.util.Random;

/**
 * 同步语句会明确指定需要lock的对象，该lock对象会在线程之间进行共享。
 * 
 * 下面的例子定义了两个person，每个person会轮流数数，数数之前会优先抢夺共享资源lock，数完后就释放，再进行资源抢夺。
 *
 */

public class StatementSync {
	
	//Display a message, preceded by the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}
	
	private int count = 0;
	private Object lock = new Object();
	
	private class Counter implements Runnable{

		@Override
		public void run() {
			while(count<100){
				try {
					increase1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void increase1() throws InterruptedException{
		synchronized(lock){
			if(count < 100 ){
				int i = count;
				i++;
				
				//increase the time to demo the synchronized.
				Thread.sleep(new Random().nextInt(5)*50);
				
				count = i;
				threadMessage(count+"");
			}
		}
	}
	
	public static void main(String[] args) {
		new StatementSync().testDeadlock();
	}
	
	public void testDeadlock(){
		Thread person1 = new Thread(new Counter());
		Thread person2 = new Thread(new Counter());
		
		person1.start();
		person2.start();
	}
	
}
