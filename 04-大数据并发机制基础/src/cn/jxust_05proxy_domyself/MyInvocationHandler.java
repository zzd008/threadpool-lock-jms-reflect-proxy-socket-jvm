package cn.jxust_05proxy_domyself;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
 * 自定义动态代理的处理逻辑(句柄方法)
 */
public class MyInvocationHandler implements InvocationHandler{

	/*
	 * 参数：生成的代理类的对象、被代理的方法、被代理方法的传入参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("代理----");
		//对getPrice方法加上新的逻辑处理：搞促销，价格降10元
		if(method.getName().equals("getPrice")){
			System.out.println("代理搞活动，降价十元！");
			//参数为被代理类的对象、传入的参数
			int price = (Integer)method.invoke(new ServerImpl(), args);//通过反射执行被代理类中的方法
			//然后加上自己新的业务逻辑
			return price-10;
		}else{//getCount方法不做处理
			System.out.println("代理-库存不变");
			int count = (Integer)method.invoke(new ServerImpl(), args);
			return count;
		}
	}

}
