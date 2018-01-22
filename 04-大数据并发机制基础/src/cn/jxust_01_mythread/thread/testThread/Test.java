package cn.jxust_01_mythread.thread.testThread;

/*
 * wait和notify必须要获得锁后才能执行，不然会报错
 * 应用：jms消息队列中，生产者往队列中加消息，如果队列满了加不进去了就wait让线程等待。等消费者去拿，拿走后再唤醒生产者线程。（队列是加锁的）
 */
public class Test {
	public static void main(String[] args) {
		final Object o=new Object();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (o) {
						System.out.println(Thread.currentThread().getName()+"拿到了锁");
						System.out.println("业务一进行--");
						System.out.println("线程等待----");
						try {
							o.wait();//让当前线程等待，这时会将锁释放掉，这时线程是阻塞的，等待有人讲锁唤醒，线程才恢复
							System.out.println("我被唤醒了！");
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		},"thread1").start();
		
		new Thread(new Runnable() {
					
					@Override
					public void run() {
						synchronized (o) {
								System.out.println(Thread.currentThread().getName()+"拿到了锁");
								System.out.println("业务二进行--");
								System.out.println("线程唤醒----");
								o.notify();	//讲等待的线程唤醒
								System.out.println("我去唤醒等待的线程！");
						}
					}
				},"thread2").start();
	}
}
