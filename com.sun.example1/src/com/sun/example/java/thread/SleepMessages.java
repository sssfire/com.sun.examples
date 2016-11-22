package com.sun.example.java.thread;

public class SleepMessages implements Runnable{

	private Thread thread;
	
	
	
    public SleepMessages(Thread thread) {
		super();
		this.thread = thread;
	}

	/**
     * Thread.sleep ���Ե�ǰ�߳�ִ����ͣһ��ʱ��Σ�����������ʱ��Ϳ��Ը������߳�ʹ�á�
	 * sleep ������������ʽ��һ����ָ��˯��ʱ�䵽���룬����һ����ָ����˯��ʱ��Ϊ���뼶��
	 * Ȼ������Щ˯��ʱ�䲻�ܱ�֤�Ǿ�ȷ�ģ���Ϊ������ͨ���ɻ��� OS �ṩ�ģ����������ơ�
	 * ���⣬˯������Ҳ����ͨ���ж���ֹ��
	 * ���κ�����£��㲻�ܼ������ sleep ������߳�����ָ����ȷ��ʱ��Ρ�
	 * 
	 * �� sleep �Ǽ����ʱ��������һ���߳��жϵ�ǰ�߳�ʱ���� sleep �׳��쳣��
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
