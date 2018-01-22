package cn.jxust_03_jvm.outofmemory;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示堆内存溢出
 * 通过run configurations配置下列参数
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=c:/dump
 * 参数-XX：+HeapDumpOnOutOfMemoryError可以让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照以便事后进行分析,会在c盘下生成一个dump文件，用对应的插件分析就可以
 */
public class HeapOOM {
	static class OOMObject {
	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
		}
	}
}
