package cn.jxust_03_mq.topic;
import java.util.Random;

import javax.jms.JMSException;      

public class ProducerTest {      
     
    /**    
     * 生产者测试类  
     */     
    public static void main(String[] args) throws JMSException, Exception {      
        ProducerTool producer = new ProducerTool(); 
        Random random = new Random();
        for(int i=0;i<20;i++){
        	
        	Thread.sleep(random.nextInt(3)*1000);
        	
        	producer.produceMessage("Hello, world!--"+i);      
        	producer.close();
        }
        
    }      
}      

