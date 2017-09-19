package com.risetek.usbprinter;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageQueue {
	private final String url = "failover://" + "tcp://mq.yun74.com:61616";
	private Session messageSession = null;
	private MessageProducer printerProducer = null;	

	public MessageQueue() {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(url);
			Connection connection = factory.createConnection();

			messageSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = messageSession.createTopic("yun74/print");
			printerProducer = messageSession.createProducer(destination);
			
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendDatas(byte[] datas) {
		try {
			BytesMessage message = messageSession.createBytesMessage();
			message.writeBytes(datas);
			printerProducer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
