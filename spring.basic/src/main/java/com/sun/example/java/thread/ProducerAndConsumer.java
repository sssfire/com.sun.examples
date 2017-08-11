package com.sun.example.java.thread;

import java.util.Random;

/**
 * 生产者生产发送一个message，消费者消费一个message
 * 注意wait和notifyAll的用法
 *
 */

public class ProducerAndConsumer {

	public class Drop {
		
		//从生产者到消费者传递的Message
		private String message;
		
		//True: 消费者需要等待生产者发送消息,
		//False: 生产者需要等待消费者消费消息.
		private boolean empty = true;

		public synchronized String take() {
			//等待有消息.
			while (empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			//转换开关.
			empty = true;
			//告诉生产者消息已经被消费了
			notifyAll();
			return message;
		}

		public synchronized void put(String message) {
			//等待消息被消费
			while (!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			//消息状态被设置为不空
			empty = false;
			// Store message.
			this.message = message;
			//通知消费者可以消费了
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
