package com.sun.example.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.example.zookeeper.demo1.Executor;

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
	 * FixedThreadPool: 创建一个可重用固定线程集合的线程池，只有要请求的过来，就会在一个队列里等待执行。
	 * 1. 每次执行只能执行固定个数的线程，如果超过线程池设定的容量，多余的线程将进入队列(BlockingQueue)等待执行。
	 * 2. 调用submit方法将会把待执行的线程提交到线程池
	 * 3. 最后调用shutdown方法，线程池执行完所有的线程后将会自动关闭，释放线程资源和占用的内存
	 * 4. 注意：在固定线程池中执行完的线程不会自动关闭，会继续占用线程和内存资源，除非使用shutdown进行关闭 (可以使用jstack -l pid进行查看)。 
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
