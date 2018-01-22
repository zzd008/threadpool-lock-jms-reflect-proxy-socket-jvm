package cn.jxust_01_mythread.thread.testThread;

import org.junit.Test;

/*
 * 线程锁-同步代码块，同步方法
 * 锁根据不同需求可以写在run方法中，也可以写在共享代码中
 */
public class MySynchronized {
	public static void main(String[] args) {
		final MySynchronized mySynchronized = new MySynchronized();
		final MySynchronized mySynchronized2 = new MySynchronized();
		
		/*
		 * 锁对象一样就会让另一个线程等待,例：上厕所
		 */
		new Thread("thread1") {
			public void run() {
				synchronized (mySynchronized) {
				try {
					System.out.println(this.getName()+" start");
//					int i =1/0;   //如果发生异常，jvm会将锁释放
					Thread.sleep(5000);
					System.out.println(this.getName()+"醒了");
					System.out.println(this.getName()+" end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		}.start();
		new Thread("thread2") {
			public void run() {
				synchronized (mySynchronized) {         //争抢同一把锁时，线程1没释放之前，线程2只能等待
//					synchronized (mySynchronized2) {    //如果不是一把锁，可以看到两句话同时打印
					System.out.println(this.getName()+" start");
					System.out.println(this.getName()+" end");
					
				}
			}
		}.start();
		
		
		/*
		 * 同步代码块
		 */
		new Thread("thread1"){
			public void run(){
				serve();
			}
		}.start();
		new Thread("thread2"){
			public void run(){
				serve();
			}
		}.start();
	}
	
	
//	public static synchronized void serve() 同步方法
	public static void serve(){//共享的数据
		//同步代码块
		synchronized ("zzd") {
			System.out.println(Thread.currentThread().getName()+"进入");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"退出");
		}
	}
	
	@Test
	public void test1(){
		//junit测试不出效果，它不是main线程
		/*new Thread("thread1"){
			public void run(){
				serve();
			}
		}.start();
		new Thread("thread2"){
			public void run(){
				serve();
			}
		}.start();*/
	}
}
