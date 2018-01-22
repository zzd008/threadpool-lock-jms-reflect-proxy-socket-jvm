package cn.jxust_05proxy_domyself;

/*
 * 商品服务的实现类
 */
public class ServerImpl implements Server{

	@Override
	public int getPrice(String size) {
		System.out.println("获取价格-----");
		return 100;
	}

	@Override
	public int getCount(String name) {
		System.out.println("获取库存-----");
		return 999;
	}

}
