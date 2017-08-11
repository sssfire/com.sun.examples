package com.sun.example.designpattern.factory.abstractfactory;

public class SendMailFactory implements Provider {  
    
    @Override  
    public Sender produce(){  
        return new MailSender();  
    }  
}  
