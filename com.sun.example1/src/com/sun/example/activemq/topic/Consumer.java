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
        //�ڴ���Sessionʱ������������������һ���������Ƿ�ʹ�����񣬵ڶ���������������������ȷ����Ϣ�Ѿ����յķ�ʽ��
        //�����ַ�ʽ��
        //AUTO_ACKNOWLEDGE(�Զ�֪ͨ)
        //CLIENT_ACKNOWLEDGE(�ͻ������о���֪ͨʱ��)
        //DUPS_OK_ACKNOWLEDGE(��ʱ//����֪ͨ)
        //���ʹ�õ��� �ͻ������о���֪ͨʱ�� ��ʽ����ô��Ҫ��MessageListener����ʽ����message.acknowledge()��֪ͨ�����������������յ�֪ͨ���ȡ��Ӧ�Ĳ�����
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