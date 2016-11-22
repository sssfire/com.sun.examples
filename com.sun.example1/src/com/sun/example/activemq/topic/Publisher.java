package com.sun.example.activemq.topic;

import java.util.Hashtable;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

/**
 * 1. 使用Topic方式 
 * （1）Publish - Subscribe 消息发布订阅机制
 * （2）topic数据默认不会存储，是无状态的。
 * （3）并不保证publisher发布的每条消息，subscriber都可以接收到。只有正在监听该topic地址的subscriber能够接收到消息。
 * （4）一对多的消息发布接受策略，监听同一个topic地址的多个subscribe都可以接收到，并通知MQ服务器
 *
 */

public class Publisher {

	protected int MAX_DELTA_PERCENT = 1;
	protected Map<String, Double> LAST_PRICES = new Hashtable<String, Double>();
	protected static int count = 10;
	protected static int total;

	protected static String brokerURL = "tcp://localhost:61616";
	protected static transient ConnectionFactory factory;
	protected transient Connection connection;
	protected transient Session session;
	protected transient MessageProducer producer;

	public Publisher() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		try {
			connection.start();
		} catch (JMSException jmse) {
			connection.close();
			throw jmse;
		}
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
		//(可选)设置是否持久化消息
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] args) throws JMSException {
		Publisher publisher = new Publisher();
		while (total < 1000) {
			for (int i = 0; i < count; i++) {
				publisher.sendMessage(args);
			}
			total += count;
			System.out.println("Published '" + count + "' of '" + total + "' price messages");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException x) {
			}
		}
		publisher.close();
	}

	protected void sendMessage(String[] stocks) throws JMSException {
		int idx = 0;
		while (true) {
			idx = (int) Math.round(stocks.length * Math.random());
			if (idx < stocks.length) {
				break;
			}
		}
		String stock = stocks[idx];
		Destination destination = session.createTopic("STOCKS." + stock);
		Message message = createStockMessage(stock, session);
		System.out.println(
				"Sending: " + ((ActiveMQMapMessage) message).getContentMap() + " on destination: " + destination);
		producer.send(destination, message);
	}

	protected Message createStockMessage(String stock, Session session) throws JMSException {
		Double value = LAST_PRICES.get(stock);
		if (value == null) {
			value = new Double(Math.random() * 100);
		}

		// lets mutate the value by some percentage
		double oldPrice = value.doubleValue();
		value = new Double(mutatePrice(oldPrice));
		LAST_PRICES.put(stock, value);
		double price = value.doubleValue();

		double offer = price * 1.001;

		boolean up = (price > oldPrice);

		MapMessage message = session.createMapMessage();
		message.setString("stock", stock);
		message.setDouble("price", price);
		message.setDouble("offer", offer);
		message.setBoolean("up", up);
		return message;
	}

	protected double mutatePrice(double price) {
		double percentChange = (2 * Math.random() * MAX_DELTA_PERCENT) - MAX_DELTA_PERCENT;

		return price * (100 + percentChange) / 100;
	}

}
