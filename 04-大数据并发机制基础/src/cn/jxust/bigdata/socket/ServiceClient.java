package cn.jxust.bigdata.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * 客户端
 */
public class ServiceClient {

	public static void main(String[] args) throws Exception {
		
		/*ServiceIterface service = ProxyUtils.getProxy(ServiceIterface.class,"methodA",hostname,port);
		Result = service.methodA(parameters);*/
		
		// 向服务器发出请求建立连接
		Socket socket = new Socket("localhost", 8899);
		// 从socket中获取输入输出流
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();

		PrintWriter pw = new PrintWriter(outputStream);
		pw.println("hello");
		pw.println("end");
		pw.flush();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));//客户端接收数据也是阻塞的，要等服务端做出相应
		String result = br.readLine();
		System.out.println("客户端收到："+result);
		
		/*
		 * 开启流后流会一直在，知道关闭流
		 */
		inputStream.close();
		outputStream.close();
		socket.close();
		
		
	}
}
