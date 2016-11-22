package com.sun.example.designpattern.factory.abstractfactory;

/**
 * - ��������ģʽ��һ��������ǣ���Ĵ������������࣬Ҳ����˵�������Ҫ��չ���򣬱���Թ���������޸ģ���Υ���˱հ�ԭ��
 * - ���󹤳�ģʽ�����Դ�����������࣬����һ����Ҫ�����µĹ��ܣ�ֱ�������µĹ�����Ϳ����ˣ�����Ҫ�޸�֮ǰ�Ĵ��롣
 * 
 * - 1. �ṩ���󹤳��ӿ�Provider�����еĹ�����ʵ�ָýӿڡ�
 * - 2. ����ʱ�������󹤳���ʵ������ʹ�ó��󹤳�������������
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        Provider provider = new SendMailFactory();  
	        Sender sender = provider.produce();  
	        sender.Send();  
	    }  
 
}
