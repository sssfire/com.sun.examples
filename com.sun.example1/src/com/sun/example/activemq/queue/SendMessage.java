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
 * 1. ʹ��Queue��ʽ
 * ��1��Queue����Ĭ�ϻ���MQ�����������ļ���ʽ���档Active MQһ�㱣����$MQ_HOME/data/kr-store/data���棬Ҳ����ͨ��������DB�д洢
 * ��2��Queue���Ա�֤ÿ�����ݶ��ܱ�Receive����
 * ��3��Sender������Ϣ��Ŀ��Queue��receiver�����첽������Щ��Ϣ��Queue�ϵ���Ϣ���û��Receiver���ܣ���־û���JMS�У����ᶪʧ��
 * ��4��һ��һ����Ϣ�������ܲ��ԣ�һ��sender���͵���Ϣ��ֻ����һ��receiver���ܡ�receiver���ܺ󣬻�֪ͨMQ��������ȡɾ��������
 * 
 * 2. ��Ϣ��������JMS������Ϣ�Ĳ��裺
 *    S1. ��������ʹ�õĹ�����JMS ConnectionFactory
 *    S2. ʹ�ù������JMS ConnectionFactory��������Connection
 *    S3. ʹ��Connection������JMS
 *    S4. ͨ��Connection��JMS�ϴ����ỰSession
 *    S5. ʹ�ûỰSession����Ŀ�����Destination
 *    S6. ʹ�ûỰsession������Ϣ������MessageProducer
 *    S7. ������Ϣ����
 *    S8. ʹ����Ϣ������MessageProducer������Ϣ
 *    S9. �ر�PRoducer���ر�Session���ر�����
 *
 */

public class SendMessage {
	private static final String url = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "choice.queue";
	protected String expectedBody = "<hello>world!</hello>";

	public void sendMessage() throws JMSException {
		Connection connection = null;
		try {
			//S1. �������ӹ���
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			//S2. ��������
			connection = (Connection) connectionFactory.createConnection();
			//S3. ʹ�����ӣ�������JMS
			connection.start();
			//S4. �������Ӷ��󴴽��ػ�Session
			Session session = (Session) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//S5. ʹ��Session���󴴽�Ŀ�����
			Destination destination = session.createQueue(QUEUE_NAME);
			//S6. ������Ϣ�ṩ��
			MessageProducer producer = session.createProducer(destination);
			//S7. �����ı���Ϣ����
			TextMessage message = session.createTextMessage(expectedBody);
			message.setStringProperty("headname", "remoteB");
			//S8. ������Ϣ
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