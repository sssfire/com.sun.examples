package com.sun.example.java.concurrent;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferDemo {

	class BoundedBuffer {
		final Lock lock = new ReentrantLock();
		final Condition notFull = lock.newCondition();
		final Condition notEmpty = lock.newCondition();

		final Object[] items = new Object[5];
		int putptr, takeptr, count;

		public void put(Object x) throws InterruptedException {
			lock.lock();

			try {
				while (count == items.length) {
					System.out.println(new Date() + " put  is to wait....");
					notFull.await();
				}
				items[putptr] = x;
				if (++putptr == items.length) {
					putptr = 0;
				}
				++count;
				notEmpty.signal();
			} finally {
				lock.unlock();
			}
		}

		public Object take() throws InterruptedException {
			lock.lock();
			try {
				while (count == 0) {
					System.out.println(new Date() + " take is going to wait..");
					notEmpty.await();
				}
				Object x = items[takeptr];
				if (++takeptr == items.length) {
					takeptr = 0;
				}
				--count;
				notFull.signal();
				return x;
			} finally {
				lock.unlock();
			}

		}
	}

	public static void main(String[] args) {
		BoundedBufferDemo demo = new BoundedBufferDemo();
		demo.puttakeTest();
	}
	
	public void puttakeTest() {

		final BoundedBuffer bb = new BoundedBuffer();
		int count = 10;
		final CountDownLatch c = new CountDownLatch(count * 2);

		System.out.println(new Date() + " now try to call put for " + count);

		for (int i = 0; i < count; ++i) {
			final int index = i;
			try {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							bb.put(index);
							System.out.println(new Date() + "  put finished:  " + index);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						c.countDown();
					}
				});
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			System.out.println(new Date() + " main thread is going to sleep for 10 seconds");
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out.println(new Date() + " now try to take for count: " + count);
		for (int i = 0; i < count; ++i) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Object o = bb.take();
						System.out.println(new Date() + " take get: " + o);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					c.countDown();
				}
			});
			t.start();
		}

		try {
			System.out.println(new Date() + ": main thread is to wait for all threads");
			c.await();
		} catch (
		InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(new Date() + " all threads finished");
	}

}
