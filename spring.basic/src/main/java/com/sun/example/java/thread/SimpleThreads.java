package com.sun.example.java.thread;

import java.util.Random;

/**
 * 
 * join方法允许一个线程等待另一个完成。t.join()它会导致当前线程暂停执行直到t线程终止。
 * join 允许程序员指定一个等待周期。
 * 与 sleep 一样，等待时间是依赖于操作系统的时间，不能假设 join 等待时间是精确的。
 * 像 sleep 一样，join 响应中断并通过 InterruptedException 退出。
 * 
 * 
 * SimpleThreads 示例，有两个线程，第一个线程是每个 Java 应用程序都有主线程。
 * 主线程创建的 Runnable 对象 MessageLoop，并等待它完成。如果 MessageLoop 需要很长时间才能完成，主线程就中断它。
 * 该 MessageLoop 线程打印出一系列消息。如果中断之前就已经打印了所有消息，则 MessageLoop 线程打印一条消息并退出。
 */
public class SimpleThreads {
	
	//Display a message, preceded by the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {
		
		public void run() {
			String importantInfo[] = { "Mares eat oats", "Does eat oats", "Little lambs eat ivy",
					"A kid will eat ivy too" };
			try {
				for (int i = 0; i < importantInfo.length; i++) {
					// Pause for 4 seconds
					Thread.sleep(4000);
					// Print a message
					threadMessage(importantInfo[i]);
				}
			} catch (InterruptedException e) {
				threadMessage("I wasn't done!");
			}
		}
		
	}

	public static void main(String args[]) throws InterruptedException {

		//Delay, in milliseconds before we interrupt MessageLoop thread (default 12 seconds).
		long patience = 1000 * 12;

		//If command line argument present, gives patience in seconds.
		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			} catch (NumberFormatException e) {
				System.err.println("Argument must be an integer.");
				System.exit(1);
			}
		}

		threadMessage("Starting MessageLoop thread");
		long startTime = System.currentTimeMillis();
		//异步执行t线程
		Thread t = new Thread(new MessageLoop());
		t.start();

		threadMessage("Waiting for MessageLoop thread to finish");
		
		//循环直到t线程结束
		while (t.isAlive()) {
			
			//使用随机数指定主线程等待时间
			int waitingtime = new Random().nextInt(7); 
			threadMessage("Will wait " + waitingtime + " seconds ...");
			
			//主线程等待waitingtime秒后继续执行后续代码，如果等待过程中t线程结束，则也会继续执行后续代码
			t.join(waitingtime * 1000 + 1);
			
			//如果等待超时，且t线程未结束，则中断t线程执行，并等待t线程结束。
			if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
				threadMessage("Tired of waiting!");
				t.interrupt();
				// Shouldn't be long now and wait indefinitely
				t.join();
			}
		}
		
		threadMessage("Finally!");
	}
}