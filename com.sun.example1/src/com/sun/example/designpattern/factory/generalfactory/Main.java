package com.sun.example.designpattern.factory.generalfactory;

/**
 * - 工厂模式适用于有大量相同产品需要创建的地方，具有共同的接口。
 * - 工厂模式的缺点是类的创建依赖于具体的工厂类。如果需要扩展程序，需要对工厂类进行扩展，会违背闭包原则
 * 
 * - 普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建
 * - 该方法通过向工厂传递参数的方式创建不同的对象。
 * - 如果参数名称错误，则创建错误。
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        SendFactory factory = new SendFactory();  
	        Sender sender = factory.produce("sms");  
	        sender.Send();  
	    }  
 
}
