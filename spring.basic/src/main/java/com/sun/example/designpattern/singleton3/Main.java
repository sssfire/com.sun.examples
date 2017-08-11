package com.sun.example.designpattern.singleton3;

/**
 * 
 * - 使用内部静态类实现单利模式
 * - 检查输出是否有不同的类实例
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
