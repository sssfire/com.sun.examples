package com.sun.example.designpattern.singleton3;

/**
 * 
 * - ʹ���ڲ���̬��ʵ�ֵ���ģʽ
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
