package cn.jxust_01_mythread.volatiletest;
/*
 * volatile是线程不安全的
 */

public class TestVolatile {
	
	private volatile static int num=0;
	public static void main(String[] args) throws Exception {
		for(int i=1;i<=100;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j=1;j<=1000;j++){
						num++;
					}
				}
			}).start();
		}
		System.out.println(num);
//		Thread.sleep(5000);
	}
}