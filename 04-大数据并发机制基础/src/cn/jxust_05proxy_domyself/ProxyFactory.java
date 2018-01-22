package cn.jxust_05proxy_domyself;

import java.lang.reflect.Proxy;

/*
 * 代理对象工厂，生产代理对象
 */
public class ProxyFactory {
	public static Object getServerProxy(){
		/*
		 * 参数：被代理的接口的类加载器、被代理的接口的实现类的所有实现接口、句柄方法
		 * 会根据反射，产生一个被代理类的对象
		 * 过程：产生一个代理类，由代理类产生代理对象
		 */
//		return Proxy.newProxyInstance(Server.class.getClassLoader(), ServerImpl.class.getInterfaces(), new MyInvocationHandler()); error!
//		return Proxy.newProxyInstance(Server.class.getClassLoader(), ServerImpl.class.getInterfaces(), new MyInvocationHandler());
//		return Proxy.newProxyInstance(Server.class.getClassLoader(), new Class[] { Server }, new MyInvocationHandler());
		/*代理类实现了Proxy和ServerImpl.class.getInterfaces()中的接口，即：
		 * public final class $Proxy0 extends Proxy implements Server {} ，所以能强转成Server Server s = (Server)ProxyFactory.getServerProxy();
		 */
		return Proxy.newProxyInstance(ServerImpl.class.getClassLoader(), ServerImpl.class.getInterfaces(), new MyInvocationHandler());
	}
}
