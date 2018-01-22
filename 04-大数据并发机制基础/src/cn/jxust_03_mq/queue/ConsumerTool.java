package cn.jxust_03_mq.queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * 一对一（点对点）模型-消费者
 * 要继承MessageListener,ExceptionListener进行监听
 */
public class ConsumerTool implements MessageListener,ExceptionListener {
	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String subject = "myqueue";
	private Destination destination = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private ActiveMQConnectionFactory connectionFactory=null;
	public static Boolean isconnection=false;
	// 初始化
	private void initialize() throws JMSException {
			connectionFactory= new ActiveMQConnectionFactory(
				user, password, url);
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(subject);
			consumer = session.createConsumer(destination);
	}

	// 消费消息
	public void consumeMessage() throws JMSException {
			initialize();
			connection.start();
			
			consumer.setMessageListener(this);//会调用该类的onMessage、onException方法进行监听
			connection.setExceptionListener(this);
			System.out.println("Consumer:->Begin listening...");
			isconnection=true;
			// 开始监听 会一注进行监听，并在onMessage方法中对消息进行处理
//			Message message = consumer.receive();
//			System.out.println(message.getJMSMessageID());
	}

	// 关闭连接
	public void close() throws JMSException {
			System.out.println("Consumer:->Closing connection");
			if (consumer != null)
				consumer.close();
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
	}

	// 消息处理函数
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				System.out.println("Consumer:->Received: " + msg);
			} else {
				System.out.println("Consumer:->Received: " + message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void onException(JMSException arg0){
		isconnection=false;
	}
}
