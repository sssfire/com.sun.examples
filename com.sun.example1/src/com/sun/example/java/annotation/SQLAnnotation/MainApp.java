package com.sun.example.java.annotation.SQLAnnotation;

public class MainApp {  
    public static void main(String[] args) {  
        User u1 = new User();  
        u1.setId(9);  //��ѯidΪ9���û�  
          
        User u2 = new User();  
        u2.setUserName("JTZeng");   //ģ����ѯ�û���ΪJTZeng���û�  
        u2.setAge(21);  
          
        User u3 = new User();  
        u3.setEmail("123@163.com,123@qq.com");  //��ѯ����������һ�����û�  
          
        String sql1 = QueryGenerator.query(u1);    //��ѯidΪ9���û�  
        String sql2 = QueryGenerator.query(u2);    //��ѯ�û���ΪJTZeng������Ϊ21���û�  
        String sql3 = QueryGenerator.query(u3);    //��ѯ������������һ�����û�  
          
        System.out.println(sql1);  
        System.out.println(sql2);  
        System.out.println(sql3);  
    }  
}  