package cn.jxust_05proxy_domyself;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
 * 注意动态代理只能代理接口
 * https://www.cnblogs.com/xiaoluo501395377/p/3383130.html
 * Proxy.newProxyInstance产生代理类，由代理类产生代理对象
 * 然后再重写句柄方法，在里面定义逻辑
 * 代理对象调用方法和以前一样，只。以前是调用实现类中的方法，而代理对象是调用句柄方法invoke，通过method的invoke方法进行反射来执行实现类中的方法，拿到返回值（实现类中的该方法可能没有返回值）后定义自己新的业务逻辑。
 * 动态生成的代理类默认已经继承了Proxy类。而java是单继承，所以无法代理一个类，只能根据接口来进行代理！所以，只能代理接口，而无法代理一个具体的类。
 * 动态代理后生成的代理类是这个样子的：public final class $Proxy0 extends Proxy implements YourInterface {}
 * 
 */
public class Test {
	//不代理
	@org.junit.Test
	public void test1(){
		ServerImpl s=new ServerImpl();
		System.out.println("xxl尺寸的衣服价格是"+s.getPrice("xxl"));
		System.out.println("adidas商品的库存是"+s.getCount("adidas"));
	}
	
	//代理1
	@org.junit.Test
	public void test2(){
		try {
			/*代理类实现了Proxy和ServerImpl.class.getInterfaces()中的接口，即：
			 * public final class $Proxy0 extends Proxy implements Server {} ，所以能强转成Server Server s = (Server)ProxyFactory.getServerProxy();而不能ServerImpl s = (ServerImpl)ProxyFactory.getServerProxy();
			 */
			Server s = (Server)ProxyFactory.getServerProxy();//强转成被代理的接口而不是接口的实现类
			System.out.println(s.getClass().getName());//会生成一个代理类，由代理类生成一个代理对象
			
			System.out.println("xxl尺寸的衣服价格是"+s.getPrice("xxl"));
			System.out.println("adidas商品的库存是"+s.getCount("adidas"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//代理2
	@org.junit.Test
	public void test3(){
		Server s=(Server)Proxy.newProxyInstance(ServerImpl.class.getClassLoader(), ServerImpl.class.getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("进入代理--");
				if(method.getName().equals("getPrice")){
					return 88;
				}
				else{
					return (Integer)method.invoke(new ServerImpl(), args);
				}
			}
		});
		System.out.println(s.getPrice("aa"));
		System.out.println(s.getCount("bb"));
		
	}
}
