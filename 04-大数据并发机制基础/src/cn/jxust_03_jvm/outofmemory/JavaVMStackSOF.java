package cn.jxust_03_jvm.outofmemory;

/**
 * 栈溢出：一个线程的递归调用太深出不来
 * 虚拟机栈和本地方法栈OOM测试
 * VM Args：-Xss128k
 */
public class JavaVMStackSOF {
	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length：" + oom.stackLength);
			throw e;
		}
	}
}