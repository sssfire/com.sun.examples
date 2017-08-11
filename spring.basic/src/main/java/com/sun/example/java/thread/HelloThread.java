package com.sun.example.java.thread;

public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        (new HelloThread()).start();
    }
}
