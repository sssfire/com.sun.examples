package com.sun.example.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在jdk 1.4时代，线程间的同步主要依赖于synchronized关键字，本质上该关键字是一个对象锁，
 * 可以加在不同的instance上或者class上，从使用的角度则分别可以加在非静态方法，静态方法，
 * 以及直接synchronized(MyObject)这样的用法；
 * 
 * concurrent包提供了一个可以替代synchronized关键字的ReentrantLock，简单的说
 * 你可以new一个ReentrantLock， 然后通过lock.lock和lock.unlock来获取锁和释放锁；
 * 
 * 注意必须将unlock放在finally块里面，
 * 
 * Reentrantlock的好处:
 * 1. 提供更好的性能。
 * 2. 提供同一个lock对象上不同condition的信号通知。
 * 3. 还提供lockInterruptibly这样支持响应中断的加锁过程，意思是说你试图去加锁，但是
 * 当前锁被其他线程hold住，然后你这个线程可以被中断；
 *
 */

public class ReentrantLockDemo {
	private int count = 10;

	private SafeSeqWithLock safeSeq = new SafeSeqWithLock();

	private CountDownLatch cdl = new CountDownLatch(count);

	class SafeSeqWithLock {

		private int i = 0;
		private ReentrantLock lock = new ReentrantLock();

		public void increase() {
			lock.lock();
			i++;
			lock.unlock();
		}

		public int get() {
			return i;
		}
	}

	class CountThread implements Runnable {

		@Override
		public void run() {

			for (int i = 0; i < 100000; i++) {
				safeSeq.increase();
			}

			System.out.println(Thread.currentThread().getName() + " is finished");

			cdl.countDown();
		}

	}

	public void testCountDown(ReentrantLockDemo demo) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			new Thread(demo.new CountThread()).start();
		}

		cdl.await();

		System.out.println("Thread[" + Thread.currentThread().getName() + "] - SafeSeq: " + safeSeq.get());
	}

	public static void main(String[] args) throws InterruptedException {
		ReentrantLockDemo demo = new ReentrantLockDemo();
		demo.testCountDown(demo);
	}
}
