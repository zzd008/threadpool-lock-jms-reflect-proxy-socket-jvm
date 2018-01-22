package cn.jxust_02_blockingqueue.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.jxust_02_blockingqueue.consumer.Consumer;
import cn.jxust_02_blockingqueue.producer.Producer;

/*
 * 主测试类
 */
public class Test {
	public static void main(String[] args) throws Exception {
		//才用链表消息队列，固定长度为2
//		LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>();
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
		// BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		// 不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE
		// BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
		Consumer consumer = new Consumer(queue);
		Producer producer = new Producer(queue);
		for (int i = 0; i < 3; i++) {
			new Thread(producer, "Producer" + (i + 1)).start();
		}
		for (int i = 0; i < 5; i++) {
			new Thread(consumer, "Consumer" + (i + 1)).start();
		}
		
		/*
		 * 4个生产者，五个消费者，有一个消费者会拿不到队列中的东西，所以程序会阻塞
		 */
		new Thread(producer, "Producer" + (5)).start();
	}
}
