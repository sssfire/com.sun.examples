package com.sun.example.java.thread;

import java.util.Random;

/**
 * ͬ��������ȷָ����Ҫlock�Ķ��󣬸�lock��������߳�֮����й���
 * 
 * ��������Ӷ���������person��ÿ��person����������������֮ǰ���������Ṳ����Դlock���������ͷţ��ٽ�����Դ���ᡣ
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
