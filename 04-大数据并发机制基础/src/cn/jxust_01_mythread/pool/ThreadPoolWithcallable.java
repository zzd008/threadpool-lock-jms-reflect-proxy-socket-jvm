package cn.jxust_01_mythread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * callable 跟runnable的区别：
 * runnable的run方法不会有任何返回结果，所以主线程无法获得任务线程的返回值
 * 
 * callable的call方法可以返回结果，但是主线程在获取时是被阻塞，需要等待任务线程返回才能拿到结果
 * @author
 *
 */
public class ThreadPoolWithcallable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService pool = Executors.newFixedThreadPool(4);//每次最多同时执行四个线程，剩下的线程要进行轮片 
		
		/*pool.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("以runnable方式让线程执行任务!");
			}
		});
		
		Future<String> future = pool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "以callable方式让线程执行任务，有返回值！";
			}
		});
		System.out.println(future.get());*/
		
		
		for(int i = 0; i < 10; i++){
			//主线程通过回调来获取线程的返回值
			Future<String> submit = pool.submit(new Callable<String>(){//匿名内部类不能传参，可以在外面写一个类实现该接口，并重写构造方法
				@Override
				public String call() throws Exception {
					System.out.println("a");
					Thread.sleep(5000);
					return "b--"+Thread.currentThread().getName();
				}			   
			   });
			//从Future中get结果，这个方法是会被阻塞的，一直要等到线程任务返回结果才能拿到返回值
			//注意使用场景，主线程需要线程的返回结果时才用，不然线程发生阻塞，主线程就会一直等待。。。。
			System.out.println(submit.get());//main线程会在这里阻塞，等待5s，直到那个线程结束
		} 
			pool.shutdown();

	}

}
