package com.sun.example.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 1. ReadWriteLock ��һ�Զ�д��ص���������һ��������һ��д��
 * 2. �������Ա�����߳�ͬʱ�϶���,��д��ͬһʱ��ֻ����һ���߳���д�� 
 * 3. ͬһ���߳��У�����Ѿ����˶������Ͳ�����ӵ����Ӧ��д����������������� ������д���������϶�����Ҳ����ӵ�е������Խ��������������� 
 * 4. ��ͬ���߳��У��������Ա��ܶ��߳�ͬʱӵ�У�ֻҪ��ǰû��д���� ��ӵ��д���������������̼߳�������д����Ҳ�����ж�����
 *
 */
public class ReentrantReadWriteLockDemo {

	// ��д��
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

		// �����ڲ�ͬ��thread�л�ȡ��д��
		demo.testReadWriteLockInMutilpleThreads();
		System.out.println();

		// ���Զ�д���Ľ�������
		demo.testWriteThenReadLockInSingleThread();
		System.out.println();

		// ���Զ�д������������
		demo.testReadThenWriteLockInSingleThread();
		System.out.println();
	}

	/**
	 * 1. ReadLock���Ա�����߳�ͬʱ���
	 * 2. WriteLockֻ�ܱ�һ���̻߳��
	 * 3. WriteLock���ͷ�ǰ���ܻ��ReadLock
	 * 4. ���е�ReadLock���ͷ�ǰ���ܻ��WriteLock
	 * 5. ��������ʹ��һ������ֵcommonValue��ȷ��WriteLock��ReadLock�Ĳ���˳��
	 * ��1����WriteLockThread�̻߳�ȡWriteLock�ڼ䣬��д��ֵ���ܱ�����WriteLockThread�߳��޸ģ�˵������WriteLockThread���ڵȴ�״̬�����ܶ�WriteLock������
	 * ��2����WriteLockThread�̻߳�ȡWriteLock�ڼ䣬��д�Ĳ���"marked"���м�ֵ���ܱ�����ReadLockThread�̶߳�ȡ����ֻ�ܶ�ȡ��"marked"������ֵ��˵������ReadLockThread���ڵȴ�״̬�����ܶ�ReadLock������
	 * ��3����ReadLockThread�̻߳�ȡReadLock�ڼ䣬���δ�ӡ����ȡ��ֵû�иı䣬˵����ʱû������WriteLockThread�޸ĸ�ֵ�����е�WriteLockThread�����ڵȴ�״̬�����ܶ�WriteLock������
	 * ��4����ReadLockThread�̻߳�ȡReadLock�ڼ䣬����ȡ��ֵ��������ReadLockThreadҲ����ͬʱ��ȡ��˵������ReadLockThread����ͬ����ReadLock������
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
	 * ��һ���߳��У�����Ѿ����˶������Ͳ�������Ϊд����������������� ����Ĵ�����������
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
		// ��������
		wLock.lock();
		System.out.println("unlock the read lock");
		rLock.unlock();
		System.out.println("unlock the write lock");
		wLock.unlock();
		System.out.println("--------End--------");
	}

	/**
	 * ��һ���߳��У������Ȼ��д�����ٻ�ö���
	 */
	public void testWriteThenReadLockInSingleThread() throws InterruptedException {
		System.out.println("Test Write then Read Lock in a single thread!");
		WriteLock wLock = wrLock.writeLock();
		ReadLock rLock = wrLock.readLock();

		System.out.println("Get the write lock");
		wLock.lock();
		System.out.println("Get the read lock");
		rLock.lock();
		// ��д���ͷŵ�˳������ν
		System.out.println("unlock the write lock");
		wLock.unlock();
		System.out.println("unlock the read lock");
		rLock.unlock();
		System.out.println("--------End--------");
	}

}
