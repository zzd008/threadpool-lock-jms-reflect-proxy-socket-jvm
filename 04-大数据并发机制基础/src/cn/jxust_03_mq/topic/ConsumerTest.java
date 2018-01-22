package cn.jxust_03_mq.topic;

import javax.jms.JMSException;

/*
 * 消费测试类
 * Queue（点对点）方式和Topic（发布/订阅）方式 的运行结果最明显的区别是：在Queue（点对点）方式中先运行生产者,再运行消费者,消费者还能接受到消息；
	而Topic（发布/订阅）方式就不同了，先运行发布者，再运行订阅者，订阅者收到的消息
	可能没有或者是不完全的。
 */
public class ConsumerTest implements Runnable {
	static Thread t1 = null;

	/**
	 */
	public static void main(String[] args) throws InterruptedException {

		t1 = new Thread(new ConsumerTest());
		t1.setDaemon(false);
		t1.start();
		/**
		 * 如果发生异常，则重启consumer
		 */
		/*while (true) {
			System.out.println(t1.isAlive());
			if (!t1.isAlive()) {
				t1 = new Thread(new ConsumerTest());
				t1.start();
				System.out.println("重新启动");
			}
			Thread.sleep(5000);
		}*/
		// 延时500毫秒之后停止接受消息
		// Thread.sleep(500);
		// consumer.close();
	}

	public void run() {
		try {
			ConsumerTool consumer = new ConsumerTool();
			consumer.consumeMessage();
			while (ConsumerTool.isconnection) {
				
			}
		} catch (Exception e) {
		}

	}
}
