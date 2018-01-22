package cn.jxust_03_mq.queue;
import javax.jms.JMSException;      

public class ProducerTest {      
     
    /**    
     * 一对一生产者测试
     */     
    public static void main(String[] args) throws JMSException, Exception{      
        ProducerTool producer = new ProducerTool();     
        producer.produceMessage("Hello, world!");      
        producer.close();
    }      
}      

