package com.sun.example.activemq.queue;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * 1. 使用Queue方式
 * （1）Queue数据默认会在MQ服务器上以文件形式保存。Active MQ一般保存在$MQ_HOME/data/kr-store/data下面，也可以通过配置在DB中存储
 * （2）Queue可以保证每条数据都能被Receive接受
 * （3）Sender发送消息到目标Queue，receiver可以异步接收这些消息。Queue上的消息如果没有Receiver接受，会持久化在JMS中，不会丢失。
 * （4）一对一的消息发布接受策略，一个sender发送的消息，只能有一个receiver接受。receiver接受后，会通知MQ服务器采取删除操作。
 * 
 * 2. 消息生产者向JMS发送消息的步骤：
 *    S1. 创建连接使用的工厂类JMS ConnectionFactory
 *    S2. 使用管理对象JMS ConnectionFactory建立连接Connection
 *    S3. 使用Connection连接至JMS
 *    S4. 通过Connection在JMS上创建会话Session
 *    S5. 使用会话Session创建目标队列Destination
 *    S6. 使用会话session创建消息生产者MessageProducer
 *    S7. 创建消息对象
 *    S8. 使用消息生产者MessageProducer发送消息
 *    S9. 关闭PRoducer，关闭Session，关闭连接
 *
 */

public class SendMessage {
	private static final String url = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "choice.queue";
	protected String expectedBody = "<hello>world!</hello>";

	public void sendMessage() throws JMSException {
		Connection connection = null;
		try {
			//S1. 创建连接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			//S2. 创建连接
			connection = (Connection) connectionFactory.createConnection();
			//S3. 使用连接，连接至JMS
			connection.start();
			//S4. 根据连接对象创建回话Session
			Session session = (Session) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//S5. 使用Session对象创建目标队列
			Destination destination = session.createQueue(QUEUE_NAME);
			//S6. 创建消息提供者
			MessageProducer producer = session.createProducer(destination);
			//S7. 创建文本消息对象
			TextMessage message = session.createTextMessage(expectedBody);
			message.setStringProperty("headname", "remoteB");
			//S8. 发送消息
			producer.send(message);
			producer.close();
			session.commit();
			session.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SendMessage sndMsg = new SendMessage();
		try {
			sndMsg.sendMessage();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}