package com.sun.example.java.thread;

import java.util.Random;

/**
 * 
 * join��������һ���̵߳ȴ���һ����ɡ�t.join()���ᵼ�µ�ǰ�߳���ִͣ��ֱ��t�߳���ֹ��
 * join �������Աָ��һ���ȴ����ڡ�
 * �� sleep һ�����ȴ�ʱ���������ڲ���ϵͳ��ʱ�䣬���ܼ��� join �ȴ�ʱ���Ǿ�ȷ�ġ�
 * �� sleep һ����join ��Ӧ�жϲ�ͨ�� InterruptedException �˳���
 * 
 * 
 * SimpleThreads ʾ�����������̣߳���һ���߳���ÿ�� Java Ӧ�ó��������̡߳�
 * ���̴߳����� Runnable ���� MessageLoop�����ȴ�����ɡ���� MessageLoop ��Ҫ�ܳ�ʱ�������ɣ����߳̾��ж�����
 * �� MessageLoop �̴߳�ӡ��һϵ����Ϣ������ж�֮ǰ���Ѿ���ӡ��������Ϣ���� MessageLoop �̴߳�ӡһ����Ϣ���˳���
 */
public class SimpleThreads {
	
	//Display a message, preceded by the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {
		
		public void run() {
			String importantInfo[] = { "Mares eat oats", "Does eat oats", "Little lambs eat ivy",
					"A kid will eat ivy too" };
			try {
				for (int i = 0; i < importantInfo.length; i++) {
					// Pause for 4 seconds
					Thread.sleep(4000);
					// Print a message
					threadMessage(importantInfo[i]);
				}
			} catch (InterruptedException e) {
				threadMessage("I wasn't done!");
			}
		}
		
	}

	public static void main(String args[]) throws InterruptedException {

		//Delay, in milliseconds before we interrupt MessageLoop thread (default 12 seconds).
		long patience = 1000 * 12;

		//If command line argument present, gives patience in seconds.
		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			} catch (NumberFormatException e) {
				System.err.println("Argument must be an integer.");
				System.exit(1);
			}
		}

		threadMessage("Starting MessageLoop thread");
		long startTime = System.currentTimeMillis();
		//�첽ִ��t�߳�
		Thread t = new Thread(new MessageLoop());
		t.start();

		threadMessage("Waiting for MessageLoop thread to finish");
		
		//ѭ��ֱ��t�߳̽���
		while (t.isAlive()) {
			
			//ʹ�������ָ�����̵߳ȴ�ʱ��
			int waitingtime = new Random().nextInt(7); 
			threadMessage("Will wait " + waitingtime + " seconds ...");
			
			//���̵߳ȴ�waitingtime������ִ�к������룬����ȴ�������t�߳̽�������Ҳ�����ִ�к�������
			t.join(waitingtime * 1000 + 1);
			
			//����ȴ���ʱ����t�߳�δ���������ж�t�߳�ִ�У����ȴ�t�߳̽�����
			if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
				threadMessage("Tired of waiting!");
				t.interrupt();
				// Shouldn't be long now and wait indefinitely
				t.join();
			}
		}
		
		threadMessage("Finally!");
	}
}