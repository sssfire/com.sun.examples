package com.sun.example.java.thread;

public class HelloRunnable implements Runnable {
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        (new Thread(new HelloRunnable())).start();
    }
}
