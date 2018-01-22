package cn.jxust_03_mq.queue;


public class ConsumerTest implements Runnable {
	static Thread t1 = null;
	public static void main(String[] args) throws InterruptedException {

		t1 = new Thread(new ConsumerTest());
		t1.start();
	}

	public void run() {
		try {
			ConsumerTool consumer = new ConsumerTool();
			consumer.consumeMessage();
			/*while (ConsumerTool.isconnection) {	
				//System.out.println(123);
			}*/
		} catch (Exception e) {
		}

	}
}
