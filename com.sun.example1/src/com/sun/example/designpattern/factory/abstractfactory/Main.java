package com.sun.example.designpattern.factory.abstractfactory;

/**
 * - 工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则
 * - 抽象工厂模式，可以创建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
 * 
 * - 1. 提供抽象工厂接口Provider，所有的工厂类实现该接口。
 * - 2. 调用时创建抽象工厂的实例，在使用抽象工厂方法创建对象。
 */

public class Main {
	  
	    public static void main(String[] args) {  
	        Provider provider = new SendMailFactory();  
	        Sender sender = provider.produce();  
	        sender.Send();  
	    }  
 
}
