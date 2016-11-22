package com.sun.example.java.thread;

public class SleepMessages implements Runnable{

	private Thread thread;
	
	
	
    public SleepMessages(Thread thread) {
		super();
		this.thread = thread;
	}

	/**
     * Thread.sleep 可以当前线程执行暂停一个时间段，这样处理器时间就可以给其他线程使用。
	 * sleep 有两种重载形式：一个是指定睡眠时间到毫秒，另外一个是指定的睡眠时间为纳秒级。
	 * 然而，这些睡眠时间不能保证是精确的，因为它们是通过由基础 OS 提供的，并受其限制。
	 * 此外，睡眠周期也可以通过中断终止。
	 * 在任何情况下，你不能假设调用 sleep 会挂起线程用于指定精确的时间段。
	 * 
	 * 当 sleep 是激活的时候，若有另一个线程中断当前线程时，则 sleep 抛出异常。
	 * 
     */
    public static void main(String[] args){
        String importantInfo[] = { "Mares eat oats", "Does eat oats", "Little lambs eat ivy",
                "A kid will eat ivy too" };

        new Thread(new SleepMessages(Thread.currentThread())).start();
        
        for (int i = 0; i < importantInfo.length; i++) {

        	
            try {
                // Pause for 4 seconds
            	Thread.sleep(4000);
				
	            // Print a message
	            System.out.println(importantInfo[i]);
	            
	            if(Thread.interrupted()){
	            	System.out.println("thread is interrupted!");
	            }
				
			} catch (InterruptedException e) {
	            // Print a message
	            System.out.println("exception: sleep is interrupted");
			}

        }
    }

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			thread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
