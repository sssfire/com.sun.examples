package com.sun.example.designpattern.factory.staticfactory;

public class SmsSender implements Sender {  
	  
    @Override  
    public void Send() {  
        System.out.println("this is sms sender!");  
    }  
}  
