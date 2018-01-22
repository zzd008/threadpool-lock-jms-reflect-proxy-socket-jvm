package cn.jxust_01_mythread.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lockInterruptibly()获取锁
 * 观察现象：如果thread-0得到了锁，阻塞。。。thread-1尝试获取锁，如果拿不到，则可以被中断等待
 *
 */
public class MyInterruptibly {
	 private Lock lock = new ReentrantLock();  
	 
	    public static void main(String[] args)  {
	    	MyInterruptibly test = new MyInterruptibly();
//	    	MyInterruptibly test1 = new MyInterruptibly(); 注意线程中要传入同一个test对象来调用insert方法，以确保是同一把锁，不然不是同一把锁
//	    	MyThread thread0 = new MyThread(test1);
	        MyThread thread0 = new MyThread(test);
	        MyThread thread1 = new MyThread(test);
	        thread0.start();
	        thread1.start();
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        thread1.interrupt();//线程中断会抛出异常 如果是拿到了锁，线程不会中断
	        System.out.println("=====================");
	    }  
	     
	    public void insert(Thread thread) throws InterruptedException{
	        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
	        try {  
	            System.out.println(thread.getName()+"得到了锁");
	            long startTime = System.currentTimeMillis();
	            for(    ;     ;) {
	                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
	                    break;
	                //插入数据
	            }
	        }
	        finally {
	            System.out.println(Thread.currentThread().getName()+"执行finally");
	            lock.unlock();
	            System.out.println(thread.getName()+"释放了锁");
	        }  
	    }
	}
	 
	class MyThread extends Thread {
	    private MyInterruptibly test = null;
	    public MyThread(MyInterruptibly test) {
	        this.test = test;
	    }
	    @Override
	    public void run() {
	         
	        try {
	        	//注意线程中要传入同一个test对象来调用insert方法，以确保是同一把锁，不然不是同一把锁
	            test.insert(Thread.currentThread());
	        } catch (Exception e) {
	            System.out.println(Thread.currentThread().getName()+"被中断");
	        }
	    }

}
