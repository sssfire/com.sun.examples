package com.sun.example.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 1. ReadWriteLock 是一对读写相关的锁，包含一个读锁和一个写锁
 * 2. 读锁可以被多个线程同时上读锁,但写锁同一时间只能有一个线程上写锁 
 * 3. 同一个线程中，如果已经有了读锁，就不能再拥有相应的写锁，否则产生死锁； 但上了写锁可以再上读锁。也就是拥有的锁可以降级，但不能升级 
 * 4. 不同的线程中，读锁可以被很多线程同时拥有，只要当前没有写锁； 而拥有写锁的条件是所有线程即不能有写锁，也不能有读锁。
 *
 */
public class ReentrantReadWriteLockDemo {

	// 读写锁
	private ReentrantReadWriteLock wrLock = new ReentrantReadWriteLock();

	private int threadCount = 10;
	
	private String commonValue = Thread.currentThread().getName();

	private CountDownLatch countDownLatch = new CountDownLatch(threadCount * 2);


	class ReadLockThread implements Runnable {

		@Override
		public void run() {

			ReadLock rLock = wrLock.readLock();
			
			rLock.lock();
			System.out.println(Thread.currentThread().getName() + " have read lock, get value: " + commonValue);
			

			try {
				Thread.sleep(new Random().nextInt(1 * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread().getName() + " ready to release read lock, hold value: " + commonValue);
				System.out.println(Thread.currentThread().getName() + " release the read lock! ");
				rLock.unlock();
			}

			countDownLatch.countDown();

		}

	}

	class WriteLockThread implements Runnable {

		@Override
		public void run() {

			WriteLock wLock = wrLock.writeLock();

			wLock.lock();
			commonValue =  Thread.currentThread().getName();
			System.out.println("[" + Thread.currentThread().getName() + "] have write lock, write value: " + commonValue);
			 
			

			try {
				Thread.sleep(new Random().nextInt(3 * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("[" + Thread.currentThread().getName() + "] have write lock, hold value: " + commonValue);
				commonValue =  Thread.currentThread().getName() + " marked";
				System.out.println("[" + Thread.currentThread().getName() + "] release the write lock, new value: " + commonValue);
				wLock.unlock();
			}

			countDownLatch.countDown();

		}

	}

	public static void main(String[] args) throws InterruptedException {
		ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

		// 测试在不同的thread中获取读写锁
		demo.testReadWriteLockInMutilpleThreads();
		System.out.println();

		// 测试读写锁的降级处理
		demo.testWriteThenReadLockInSingleThread();
		System.out.println();

		// 测试读写锁的升级处理
		demo.testReadThenWriteLockInSingleThread();
		System.out.println();
	}

	/**
	 * 1. ReadLock可以被多个线程同时获得
	 * 2. WriteLock只能被一个线程获得
	 * 3. WriteLock在释放前不能获得ReadLock
	 * 4. 所有的ReadLock在释放前不能获得WriteLock
	 * 5. 在例子中使用一个共享值commonValue，确定WriteLock，ReadLock的操作顺序
	 * （1）在WriteLockThread线程获取WriteLock期间，所写的值不能被其他WriteLockThread线程修改，说明其他WriteLockThread处于等待状态，不能对WriteLock上锁。
	 * （2）在WriteLockThread线程获取WriteLock期间，所写的不带"marked"的中间值不能被其他ReadLockThread线程读取，而只能读取带"marked"的最终值，说明其他ReadLockThread处于等待状态，不能对ReadLock上锁。
	 * （3）在ReadLockThread线程获取ReadLock期间，两次打印所读取的值没有改变，说明此时没有其他WriteLockThread修改该值，所有的WriteLockThread都处于等待状态，不能对WriteLock上锁。
	 * （4）在ReadLockThread线程获取ReadLock期间，所读取的值对于其他ReadLockThread也可以同时获取，说明其他ReadLockThread可以同步对ReadLock上锁。
	 * @throws InterruptedException
	 */
	public void testReadWriteLockInMutilpleThreads() throws InterruptedException {
		System.out.println("Test Read and Write Lock in Mutiple Threads!");
		for (int i = 0; i < threadCount; i++) {
			new Thread(new ReadLockThread()).start();
			new Thread(new WriteLockThread()).start();
		}

		countDownLatch.await();
		System.out.println("--------End--------");
	}

	/**
	 * 在一个线程中，如果已经有了读锁，就不能升级为写锁，否则产生死锁。 下面的代码会产生死锁
	 * 
	 * @throws InterruptedException
	 */
	public void testReadThenWriteLockInSingleThread() throws InterruptedException {
		System.out.println("Test Read then Write Lock in a single thread!");
		ReadLock rLock = wrLock.readLock();
		WriteLock wLock = wrLock.writeLock();

		System.out.println("Get the read lock");
		rLock.lock();
		System.out.println("Get the write lock");
		// 产生死锁
		wLock.lock();
		System.out.println("unlock the read lock");
		rLock.unlock();
		System.out.println("unlock the write lock");
		wLock.unlock();
		System.out.println("--------End--------");
	}

	/**
	 * 在一个线程中，可以先获得写锁，再获得读锁
	 */
	public void testWriteThenReadLockInSingleThread() throws InterruptedException {
		System.out.println("Test Write then Read Lock in a single thread!");
		WriteLock wLock = wrLock.writeLock();
		ReadLock rLock = wrLock.readLock();

		System.out.println("Get the write lock");
		wLock.lock();
		System.out.println("Get the read lock");
		rLock.lock();
		// 读写锁释放的顺序无所谓
		System.out.println("unlock the write lock");
		wLock.unlock();
		System.out.println("unlock the read lock");
		rLock.unlock();
		System.out.println("--------End--------");
	}

}
