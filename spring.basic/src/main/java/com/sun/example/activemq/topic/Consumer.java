package com.sun.example.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {  
	  
    private static String brokerURL = "tcp://localhost:61616";  
    private static transient ConnectionFactory factory;  
    private transient Connection connection;  
    private transient Session session;  
      
    public Consumer() throws JMSException {  
        factory = new ActiveMQConnectionFactory(brokerURL);  
        connection = factory.createConnection();  
        connection.start();
        //在创建Session时可以有两个参数，第一个参数是是否使用事务，第二个参数是消费者向发送者确认消息已经接收的方式：
        //有三种方式：
        //AUTO_ACKNOWLEDGE(自动通知)
        //CLIENT_ACKNOWLEDGE(客户端自行决定通知时机)
        //DUPS_OK_ACKNOWLEDGE(延时//批量通知)
        //如果使用的是 客户端自行决定通知时机 方式，那么需要在MessageListener里显式调用message.acknowledge()来通知服务器。服务器接收到通知后采取相应的操作。
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
    }  
      
    public void close() throws JMSException {  
        if (connection != null) {  
            connection.close();  
        }  
    }      
      
    public static void main(String[] args) throws JMSException {  
        Consumer consumer = new Consumer();  
        for (String stock : args) {  
            Destination destination = consumer.getSession().createTopic("STOCKS." + stock);  
            MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);  
            messageConsumer.setMessageListener(new Listener());  
        }  
    }  
      
    public Session getSession() {  
        return session;  
    }  
  
}  