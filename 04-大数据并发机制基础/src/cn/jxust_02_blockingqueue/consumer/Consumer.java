package cn.jxust_02_blockingqueue.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 *消费者：从队列中取
 */
public class Consumer implements Runnable{  
    BlockingQueue<String> queue; 
    public Consumer(BlockingQueue<String> queue){  
        this.queue = queue;  
    }        
    @Override  
    public void run() {  
        try {  
        	String consumer = Thread.currentThread().getName();
        	System.out.println(consumer+"--消费进程开启");  
            String temp = queue.take();//如果队列为空，会阻塞当前线程  
            System.out.println(consumer+" get a product:"+temp);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
    
}  

