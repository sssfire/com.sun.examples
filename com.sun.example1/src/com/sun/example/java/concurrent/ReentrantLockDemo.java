package com.sun.example.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ��jdk 1.4ʱ�����̼߳��ͬ����Ҫ������synchronized�ؼ��֣������ϸùؼ�����һ����������
 * ���Լ��ڲ�ͬ��instance�ϻ���class�ϣ���ʹ�õĽǶ���ֱ���Լ��ڷǾ�̬��������̬������
 * �Լ�ֱ��synchronized(MyObject)�������÷���
 * 
 * concurrent���ṩ��һ���������synchronized�ؼ��ֵ�ReentrantLock���򵥵�˵
 * �����newһ��ReentrantLock�� Ȼ��ͨ��lock.lock��lock.unlock����ȡ�����ͷ�����
 * 
 * ע����뽫unlock����finally�����棬
 * 
 * Reentrantlock�ĺô�:
 * 1. �ṩ���õ����ܡ�
 * 2. �ṩͬһ��lock�����ϲ�ͬcondition���ź�֪ͨ��
 * 3. ���ṩlockInterruptibly����֧����Ӧ�жϵļ������̣���˼��˵����ͼȥ����������
 * ��ǰ���������߳�holdס��Ȼ��������߳̿��Ա��жϣ�
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
