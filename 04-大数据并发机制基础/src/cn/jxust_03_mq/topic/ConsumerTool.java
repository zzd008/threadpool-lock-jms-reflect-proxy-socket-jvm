package cn.jxust_03_mq.topic;
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
  
/*订阅模式
 * 消费者工具类  要继承MessageListener,ExceptionListener进行监听
 * 获取连接->构建消费者->消费消息并监听
 */
public class ConsumerTool implements MessageListener,ExceptionListener {      
    private String user = ActiveMQConnection.DEFAULT_USER;      
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;      
    private String url =ActiveMQConnection.DEFAULT_BROKER_URL;  
    
    private String subject = "mytopic";   //要订阅的主题   
    private Destination destination = null;//topic的父类      
    private Connection connection = null;      
    private Session session = null;      
    private MessageConsumer consumer = null;  
    public static Boolean isconnection=false;
    
    // 初始化      
    private void initialize() throws JMSException, Exception {      
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(      
                user, password, url);      
        connection = connectionFactory.createConnection();      
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);      
        destination = session.createTopic(subject);      
        consumer = session.createConsumer(destination);     
    }      
     
    // 消费消息      
    public void consumeMessage() throws JMSException, Exception {      
        initialize();      
        connection.start();
        consumer.setMessageListener(this);    //设置消息监听
        
       /* consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//逻辑
			}
		});*/
        
        connection.setExceptionListener(this);//设置异常监听
        isconnection=true;
        System.out.println("Consumer:->Begin listening...");      
        // 开始监听  并在onMessage中对消息进行处理
//         Message message = consumer.receive(); 
        
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
            if (message instanceof TextMessage) {      //处理文本类型的消息
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

	public void onException(JMSException arg0) {
		isconnection=false;
	}      
}      
     
