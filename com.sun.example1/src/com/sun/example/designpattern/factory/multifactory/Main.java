package com.sun.example.designpattern.factory.multifactory;

/**
 * - �����������ģʽ���Ƕ���ͨ��������ģʽ�ĸĽ�������ͨ��������ģʽ�У�������ݵ��ַ�������������ȷ�������󣬶������������ģʽ���ṩ��������������ֱ𴴽�����
 * - �޸�SendFactory����ʵ�ִ�����ͬ����Ĳ�ͬ������
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        SendFactory factory = new SendFactory();  
	        Sender sender = factory.produceSms();  
	        sender.Send();  
	    }  
 
}
