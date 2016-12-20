package com.sun.example.java.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 程序演示了thread的一些属性信息
 * 1. id，每个thread的唯一标识
 * 2. name，每个thread都有一个默认的名字，名字主要用来标识thread，以便于更好的使用一些工具进行管理，例如debugger或者JConsole等
 * 3. thread group，每个thread都属于一个特定的group，使用group可以更易于管理一组thread，例如sleep，interrupt, 修改prioirity等
 * 4. state，每个thread都有一个运行状态，代表线程当前的运行状态，通过状态可以使用jstack定位当前线程出现的问题 
 *  - NEW，thread刚刚建立，还未调用start运行
 *  - RUNNABLE，thread正在运行，处于正常运行状态
 *  - BLOCKED，thread之间存在同步访问，一个线程在synchronized之外等待另一个线程，会处于block状态。
 *  - WAITING，thread调用了wait方法之后，等待其他线程调用notify或者notifyAll，此时处于waiting状态。和Block的区别在于Block状态是
 *    线程等待进入synchronized临界区，waiting是thread调用wait处于等待状态。线程调用join状态后也会进行waiting
 *    状态等待其他线程join结束。
 *  - TIMED_WAITING thread在有限时间内等待会进入TIMED_WAITING，例如调用wait(time), join(time), sleep(time)等
 *  - TERMINATED thread已经执行完毕后的状态。
 *  5. Priority，定义线程执行的优先级顺序。
 */

public class CurrentThreadDemo {
	ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		CurrentThreadDemo demo = new CurrentThreadDemo();
		for(int i=0;i<10;i++){
			new Thread(demo.new MyThread(i)).start();
		}
	}
	
	class MyThread implements Runnable {

		private int num;
		
		public MyThread(int threadNum){
			this.num = threadNum;
		}
		
		@Override
		public void run() {
			printThreadInfo(num);
		}
	}
	
	private void printThreadInfo(int threadNum){
		lock.lock();
		System.out.println("=======>>>Thread " + threadNum);
		
		long id = Thread.currentThread().getId();
		String name = Thread.currentThread().getName();
		String group = Thread.currentThread().getThreadGroup().getName();
		int priority = Thread.currentThread().getPriority();
		String state = Thread.currentThread().getState().name();

		System.out.println(
				"ID:" + id + "\n" +
			    "Name:" + name + "\n" +
				"Group:" + group + "\n" +
			    "Priority:" + priority + "\n" +
			    "State:" + state + "\n"
		);

		lock.unlock();
	}
}
