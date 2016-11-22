package com.sun.example.designpattern.singleton2;

/**
 * 
 * - 线程安全的单例模式
 * 
 */

public class Main {

	public static void main(String[] args) {

		for (int i = 0; i < 1000; i++) {
			new Thread() {
				public void run() {
					Singleton singleton = Singleton.getInstance();
					System.out.println(singleton.toString());
				}
			}.start();
		}
	}

}
