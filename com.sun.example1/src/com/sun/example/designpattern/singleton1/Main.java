package com.sun.example.designpattern.singleton1;

/**
 * 
 * -  ��������Singleton����һ�ֳ��õ����ģʽ����JavaӦ���У����������ܱ�֤��һ��JVM�У��ö���ֻ��һ��ʵ�����ڡ�������ģʽ�м����ô���
   1. ĳЩ�ഴ���Ƚ�Ƶ��������һЩ���͵Ķ�������һ�ʺܴ��ϵͳ������
   2. ʡȥ��new��������������ϵͳ�ڴ��ʹ��Ƶ�ʣ�����GCѹ����
   3. ��Щ���罻�����ĺ��Ľ������棬�����Ž������̣����������Դ�������Ļ���ϵͳ��ȫ���ˡ�������һ�����ӳ����˶��˾��Աͬʱָ�ӣ��϶����ҳ�һ�ţ�������ֻ��ʹ�õ���ģʽ�����ܱ�֤���Ľ��׷��������������������̡�
 * 
 * - ���̰߳�ȫ�ĵ���ģʽ
 * - �������Ƿ��в�ͬ����ʵ��
 * 
 */

public class Main {

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					Singleton singleton = Singleton.getInstance();
					System.out.println(singleton.toString());
				}
			}.start();
		}
	}

}
