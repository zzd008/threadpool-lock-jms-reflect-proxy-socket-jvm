package cn.jxust_01_mythread.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock()获取锁
 * 观察现象：一个线程获得锁后，另一个线程取不到锁，不会一直等待
 *
 */
public class MyTryLock {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	static Lock lock = new ReentrantLock(); // 注意这个地方
	public static void main(String[] args) {
		
		new Thread("thread1") {
			public void run() {
				Thread thread = Thread.currentThread();
				boolean tryLock = lock.tryLock();//尝试获取锁，获取不到立即返回false，而不是一直等待
				System.out.println(thread.getName()+" "+tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "得到了锁");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
						}
						Thread.sleep(2000);//线程的执行顺序不确定，有时thread1全部执行完了，thread2才执行,可以让线程睡几秒来观察现象
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "释放了锁");
						lock.unlock();//释放锁
					}
				}
			};
		}.start();

		new Thread("thread2") {
			public void run() {
				Thread thread = Thread.currentThread();
//				boolean tryLock = lock.tryLock();//尝试获取锁，获取不到立即返回false，而不是一直等待
				boolean tryLock = false;
				try {
					tryLock = lock.tryLock(5000,TimeUnit.MILLISECONDS);//设置获取锁的等待时间，第一个是long类型，第二个参数是枚举类型，是第一个参数的单位，等待5000ms，tryLock(5,TimeUnit.DAYS)标识等待五天
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println(thread.getName()+" "+tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "得到了锁");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "释放了锁");
						lock.unlock();//释放锁
					}
				}

			};
		}.start();
	}


}
