package com.sun.example.activemq.queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceiveMessage {
	private static final String url = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "choice.queue";

	public void receiveMessage() {
		Connection connection = null;
		try {
			// S1. 创建连接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			// S2. 创建连接
			connection = connectionFactory.createConnection();
			// S3. 使用连接，连接至JMS
			connection.start();
			// S4. 根据连接对象创建回话Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// S5. 使用Session对象创建目标队列
			Destination destination = session.createQueue(QUEUE_NAME);
			// S6. 创建消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			// S7. 接受消息并处理
			consumeMessagesAndClose(connection, session, consumer);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer)
			throws JMSException {
		for (int i = 0; i < 1;) {
			//S7. 接受消息
			Message message = consumer.receive(1000);
			if (message != null) {
				i++;
				onMessage(message);
			}
		}
		System.out.println("Closing connection");
		//S8. 关闭消费者
		consumer.close();
		//S9. 关闭session
		session.close();
		//S10. 关闭连接
		connection.close();
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				System.out.println("Received: " + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		ReceiveMessage rm = new ReceiveMessage();
		rm.receiveMessage();
	}

}