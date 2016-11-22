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
			// S1. �������ӹ���
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			// S2. ��������
			connection = connectionFactory.createConnection();
			// S3. ʹ�����ӣ�������JMS
			connection.start();
			// S4. �������Ӷ��󴴽��ػ�Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// S5. ʹ��Session���󴴽�Ŀ�����
			Destination destination = session.createQueue(QUEUE_NAME);
			// S6. ������Ϣ������
			MessageConsumer consumer = session.createConsumer(destination);
			// S7. ������Ϣ������
			consumeMessagesAndClose(connection, session, consumer);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer)
			throws JMSException {
		for (int i = 0; i < 1;) {
			//S7. ������Ϣ
			Message message = consumer.receive(1000);
			if (message != null) {
				i++;
				onMessage(message);
			}
		}
		System.out.println("Closing connection");
		//S8. �ر�������
		consumer.close();
		//S9. �ر�session
		session.close();
		//S10. �ر�����
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