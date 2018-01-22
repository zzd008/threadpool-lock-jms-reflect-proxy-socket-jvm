package cn.jxust_03_mq.topic;
import javax.jms.Connection;      
import javax.jms.DeliveryMode;      
import javax.jms.Destination;      
import javax.jms.JMSException;      
import javax.jms.MessageProducer;      
import javax.jms.Session;      
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;      
import org.apache.activemq.ActiveMQConnectionFactory;      
     
/*
 * 订阅模式
 * 生产者连接activemq工具类
 * 获取连接->构建生产者、主题->构建消息->生产者发送消息至主题中
 */
public class ProducerTool {   
    private String user = ActiveMQConnection.DEFAULT_USER;         //连接使用的默认用户
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;    //连接默认密码   
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;       //连接的url
    
    private String subject = "mytopic";      //主题，一个消息要发在一个主题里（相当于一个队列）
    
    private Destination destination = null;      
    private Connection connection = null;      
    private Session session = null;      
    private MessageProducer producer = null;
    
    // 初始化      
    private void initialize() throws JMSException, Exception {   
//    	Topic topic = session.createTopic("aa");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(      
                user, password, url);      //创建连接工厂
        connection = connectionFactory.createConnection();    //从连接工厂中获取一个连接  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  //从连接中获取一个对话     参数一表示是否支持事务  参数二设置为客户端自动确认  https://www.cnblogs.com/MIC2016/p/6086321.html
        destination = session.createTopic(subject);      //创建主题至目的地
        producer = session.createProducer(destination);  //创建生产者    
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);   //  设置发送模式  参数为消息是否持久化 非持久化关闭activemq后，topic就会没了
    }
    // 发送消息      
    public void produceMessage(String message) throws JMSException, Exception {      
        initialize();      
        TextMessage msg = session.createTextMessage(message);   //   要发送的消息，在activemq中要封装成TextMessage
        connection.start();      //打开连接
        System.out.println("Producer:->Sending message: " + message);      
        producer.send(msg);      //生产者发送消息
        System.out.println("Producer:->Message sent complete!");      
    }
    // 关闭连接      
    public void close() throws JMSException {      
        System.out.println("Producer:->Closing connection");      
        if (producer != null)      
            producer.close();      
        if (session != null)      
            session.close();      
        if (connection != null)      
            connection.close();      
    }      
}        