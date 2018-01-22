package cn.jxust.bigdata.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 服务端
 */
public class ServiceServer {

	public static void main(String[] args) throws Exception {

		// 创建一个serversocket，绑定到本机的8899端口上
		ServerSocket server = new ServerSocket();//不绑定端口
//		ServerSocket server = new ServerSocket(8899); 将 ServerSocket 绑定到特定端口号--本地
		server.bind(new InetSocketAddress("localhost", 8899));//将 ServerSocket 绑定到特定地址（IP 地址和端口号）。

		// 接受客户端的连接请求;accept是一个阻塞方法，会一直等待，到有客户端请求连接才返回
		while (true) {
			Socket socket = server.accept();//接收一个客户端 一个客户端多次写消息，但只输出一次11
			System.out.println(11);
			new Thread(new ServiceServerTask(socket)).start();
		}
	}

}
