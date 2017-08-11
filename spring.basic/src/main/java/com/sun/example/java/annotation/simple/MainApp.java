package com.sun.example.java.annotation.simple;

@MyAnnotation1("this isannotation1")
public class MainApp {

        @MyAnnotation2(description = "this is annotation2", isAnnotation =true)
        public void sayHello() {
                  System.out.println("hello world!");
        }
        
        
        
        public static void main(String [] args){ 
        
        	MainApp demo = new MainApp();
        	demo.sayHello();
        	
        }
}