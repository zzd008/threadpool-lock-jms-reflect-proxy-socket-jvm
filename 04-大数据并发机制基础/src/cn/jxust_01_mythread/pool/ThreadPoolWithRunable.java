package cn.jxust_01_mythread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolWithRunable {

	
	/**
	 * 线程池的创建及执行任务
	 * 通过线程池执行线程
	 */
	public static void main(String[] args) {
		//创建一个线程池
		//只有一个线程的线程池（相当于单线程）
		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
		//拥有固定线程数的线程池，如果没有任务执行，那么线程会一直等待，一般设置大小为cpu的核数量
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		//用来调度即将执行的任务的线程池，可能是不是直接执行, 每隔多久执行一次... 策略型的
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		//只有一个线程，用来调度任务在指定时间执行
		ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		
		/*
		 * 动态伸缩的，里面默认有很多线程
		 */
		//线程池里有很多线程需要同时执行，老的可用线程将被新的任务触发重新执行，如果线程超过60秒内没执行，那么将被终止并从池中删除，
		ExecutorService pool = Executors.newCachedThreadPool();
		
		//提交任务 由线程池去执行runnable/thread中的run方法
		for(int i = 1; i < 5; i++){
//			Future<?> submit = pool.submit(new TaskRunnable(500)); 其实没有返回值
			pool.execute(new Runnable() {
				@Override
				public void run() {
//					serve();
//					serve1();
					System.out.println("thread name: " + Thread.currentThread().getName());
					try {
						Thread.sleep(5000);//当前线程睡5秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		pool.shutdown();//线程池关闭
	}
	
	public static synchronized void serve(){
		System.out.println(Thread.currentThread().getName()+"serve!");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static ReentrantLock lock = new ReentrantLock();
	public static void serve1(){
//		ReentrantLock lock = new ReentrantLock(); 不要这样写，因为这样会new出不同的lock对象
		lock.lock();
		System.out.println(Thread.currentThread().getName()+"serve1!");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
	}

}
