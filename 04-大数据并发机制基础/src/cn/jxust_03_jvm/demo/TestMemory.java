package cn.jxust_03_jvm.demo;

import java.util.ArrayList;
/**
 * 堆溢出
 * 64kb/50毫秒
 *测试：不断往内存中写数据，观察使用java visualVM监视工具
 *设置jvm运行参数为：-Xms100m -Xmx100m -XX:+UseSerialGC 内存为100m吧，这时程序会把jvm内存爆掉，报错Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class TestMemory {
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024*40];
	}

	public static void fillHeap(int num) throws Exception {
		ArrayList<OOMObject> list = new ArrayList<OOMObject>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();//放在这里是不会回收的，因为这个函数还没结束，这些局部对象还是有用的
	}

	public static void main(String[] args) throws Exception {
		Thread.sleep(20000);
		fillHeap(100);
//		fillHeap(50);
//		System.gc();/可能还没来得及做垃圾回收就爆掉了内存
		Thread.sleep(20000000);
	}
}
