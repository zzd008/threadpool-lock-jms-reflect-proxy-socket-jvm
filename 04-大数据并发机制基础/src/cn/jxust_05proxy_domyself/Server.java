package cn.jxust_05proxy_domyself;

/*
 * 商品服务接口
 */
public interface Server {
	public int getPrice(String size);//根据尺码获取价格
	public int getCount(String name);//根据商品名获取库存
	
}
