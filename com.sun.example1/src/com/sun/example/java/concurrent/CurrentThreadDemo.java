package com.sun.example.java.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ������ʾ��thread��һЩ������Ϣ
 * 1. id��ÿ��thread��Ψһ��ʶ
 * 2. name��ÿ��thread����һ��Ĭ�ϵ����֣�������Ҫ������ʶthread���Ա��ڸ��õ�ʹ��һЩ���߽��й�������debugger����JConsole��
 * 3. thread group��ÿ��thread������һ���ض���group��ʹ��group���Ը����ڹ���һ��thread������sleep��interrupt, �޸�prioirity��
 * 4. state��ÿ��thread����һ������״̬�������̵߳�ǰ������״̬��ͨ��״̬����ʹ��jstack��λ��ǰ�̳߳��ֵ����� 
 *  - NEW��thread�ոս�������δ����start����
 *  - RUNNABLE��thread�������У�������������״̬
 *  - BLOCKED��thread֮�����ͬ�����ʣ�һ���߳���synchronized֮��ȴ���һ���̣߳��ᴦ��block״̬��
 *  - WAITING��thread������wait����֮�󣬵ȴ������̵߳���notify����notifyAll����ʱ����waiting״̬����Block����������Block״̬��
 *    �̵߳ȴ�����synchronized�ٽ�����waiting��thread����wait���ڵȴ�״̬���̵߳���join״̬��Ҳ�����waiting
 *    ״̬�ȴ������߳�join������
 *  - TIMED_WAITING thread������ʱ���ڵȴ������TIMED_WAITING���������wait(time), join(time), sleep(time)��
 *  - TERMINATED thread�Ѿ�ִ����Ϻ��״̬��
 *  5. Priority�������߳�ִ�е����ȼ�˳��
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
