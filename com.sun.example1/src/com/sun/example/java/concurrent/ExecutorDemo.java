package com.sun.example.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.example.zookeeper.example1.Executor;

public class ExecutorDemo{
	
	private static class MyExecutor extends Thread{
		
		private int num=0;
		
		public MyExecutor(int num) {
			this.num = num;
		}
		
		public void run(){

			try {
				System.out.println("thread num: " + num);
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * FixedThreadPool: ����һ�������ù̶��̼߳��ϵ��̳߳أ�ֻ��Ҫ����Ĺ������ͻ���һ��������ȴ�ִ�С�
	 * 1. ÿ��ִ��ֻ��ִ�й̶��������̣߳���������̳߳��趨��������������߳̽��������(BlockingQueue)�ȴ�ִ�С�
	 * 2. ����submit��������Ѵ�ִ�е��߳��ύ���̳߳�
	 * 3. ������shutdown�������̳߳�ִ�������е��̺߳󽫻��Զ��رգ��ͷ��߳���Դ��ռ�õ��ڴ�
	 * 4. ע�⣺�ڹ̶��̳߳���ִ������̲߳����Զ��رգ������ռ���̺߳��ڴ���Դ������ʹ��shutdown���йر� (����ʹ��jstack -l pid���в鿴)�� 
 */
	public void testFixedThreadPoolExecutor(){

		ExecutorService executorService = Executors.newFixedThreadPool(200);
		for(int i=0;i<200;i++){
			//executorService.submit(new MyExecutor(i));
			executorService.execute(new MyExecutor(i));
		}
		System.out.println("submit finished!");
		//executorService.shutdown();
	}
	
	public static void main(String[] args) {
		ExecutorDemo demo = new ExecutorDemo();
		demo.testFixedThreadPoolExecutor();
	}
}
