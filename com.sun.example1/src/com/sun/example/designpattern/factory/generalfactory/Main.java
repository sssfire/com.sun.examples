package com.sun.example.designpattern.factory.generalfactory;

/**
 * - ����ģʽ�������д�����ͬ��Ʒ��Ҫ�����ĵط������й�ͬ�Ľӿڡ�
 * - ����ģʽ��ȱ������Ĵ��������ھ���Ĺ����ࡣ�����Ҫ��չ������Ҫ�Թ����������չ����Υ���հ�ԭ��
 * 
 * - ��ͨ����ģʽ�����ǽ���һ�������࣬��ʵ����ͬһ�ӿڵ�һЩ�����ʵ���Ĵ���
 * - �÷���ͨ���򹤳����ݲ����ķ�ʽ������ͬ�Ķ���
 * - ����������ƴ����򴴽�����
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        SendFactory factory = new SendFactory();  
	        Sender sender = factory.produce("sms");  
	        sender.Send();  
	    }  
 
}
