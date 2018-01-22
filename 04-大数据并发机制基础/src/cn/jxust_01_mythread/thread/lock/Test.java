package cn.jxust_01_mythread.thread.lock;

public class Test {
	public static void main(String[] args) {
		//4核心8线程
		//获取可用处理器的数量
		int num = Runtime.getRuntime().availableProcessors();
		System.out.println(num);
	}

}
