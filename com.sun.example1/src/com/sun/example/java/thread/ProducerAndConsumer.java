package com.sun.example.java.thread;

import java.util.Random;

/**
 * ��������������һ��message������������һ��message
 * ע��wait��notifyAll���÷�
 *
 */

public class ProducerAndConsumer {

	public class Drop {
		
		//�������ߵ������ߴ��ݵ�Message
		private String message;
		
		//True: ��������Ҫ�ȴ������߷�����Ϣ,
		//False: ��������Ҫ�ȴ�������������Ϣ.
		private boolean empty = true;

		public synchronized String take() {
			//�ȴ�����Ϣ.
			while (empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			//ת������.
			empty = true;
			//������������Ϣ�Ѿ���������
			notifyAll();
			return message;
		}

		public synchronized void put(String message) {
			//�ȴ���Ϣ������
			while (!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			//��Ϣ״̬������Ϊ����
			empty = false;
			// Store message.
			this.message = message;
			//֪ͨ�����߿���������
			notifyAll();
		}
	}
	
	public class Producer implements Runnable {
	    private Drop drop;

	    public Producer(Drop drop) {
	        this.drop = drop;
	    }

	    public void run() {
	        String importantInfo[] = { "Mares eat oats", "Does eat oats", "Little lambs eat ivy",
	                "A kid will eat ivy too" };
	        Random random = new Random();

	        for (int i = 0; i < importantInfo.length; i++) {
	            drop.put(importantInfo[i]);
	            try {
	                Thread.sleep(random.nextInt(5000));
	            } catch (InterruptedException e) {
	            }
	        }
	        drop.put("DONE");
	    }
	}
	
	public class Consumer implements Runnable {
	    private Drop drop;

	    public Consumer(Drop drop) {
	        this.drop = drop;
	    }

	    public void run() {
	        Random random = new Random();
	        String message;
	        for (message = drop.take(); !message.equals("DONE"); message = drop.take()) {
	            System.out.format("MESSAGE RECEIVED: %s%n", message);
	            try {
	                Thread.sleep(random.nextInt(5000));
	            } catch (InterruptedException e) {
	            }
	        }
	        
	        System.out.format("MESSAGE RECEIVED: %s%n", message);
	    }
	}
	
    public static void main(String[] args) {
    	
    	ProducerAndConsumer pac = new ProducerAndConsumer();
    	
        Drop drop = pac.new Drop();
        (new Thread(pac.new Producer(drop))).start();
        (new Thread(pac.new Consumer(drop))).start();
    }
	

}
