package com.sun.example.java.concurrent;

/**
 * ThreadLocalʵ��ԭ��
 * 1. ÿ��Thread����һ�����ر���threadLocals, ������ThreadLocal.ThreadLocalMap�����ڴ�ź����Thread��ص�ר������/ֵ
 * 2. �����߳���������ThreadLocal���͵�ʵ������tl�������߳�thread-n������ThreadLocal��ʵ������tl����set��������set������
 *    ���������߳�thread-n��Ӧ��threadLocals(��ThreadLocalMap)�������߳��е�value��Ϊkey����Ҫ���õ�ֵ��Ϊvalue����Map�С�
 * 3. ���һ�����߳����ö��tl���͵�ʵ�����������ڸ����̵߳�threadLocals�л��ж��entry���ֱ�����Щʵ������Ϊkey���д洢��
 * 4. ������߳��е�tl�����洢ʵ�ʵı�������ֻ�Ǳ������õĹ��ߡ�
 * @author I068353
 *
 */

public class ThreadLocalDemo {
	
	private final ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
	
	public static void main(String[] args){
		ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
		for(int i=0;i<5;i++){
			new Thread(threadLocalDemo.new MyThread()).start();
		}
	}
	
	public void calculate(Integer n){
		tl.set(0);
		System.out.println("Thread: "+ Thread.currentThread() + " init value: " + tl.get());
		for(int i=0;i<n;i++){
			tl.set( tl.get() + i );
		}
		System.out.println("Thread: "+ Thread.currentThread() + " value: " + tl.get());
	}
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			calculate(10000);			
		}
		
		@Override
		public String toString() {
			return "MyThread [id=" + Thread.currentThread().getId() + ", name="
					+ Thread.currentThread().getName() + "]";
		}
	}
	
}
