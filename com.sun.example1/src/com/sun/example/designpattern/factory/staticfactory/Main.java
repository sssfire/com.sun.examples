package com.sun.example.designpattern.factory.staticfactory;

/**
 * - ��̬��������ģʽ��������Ķ����������ģʽ��ķ�����Ϊ��̬�ģ�����Ҫ����ʵ����ֱ�ӵ��ü���
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        Sender sender = SendFactory.produceSms();  
	        sender.Send();  
	    }  
 
}
