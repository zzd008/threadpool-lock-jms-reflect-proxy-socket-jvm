package cn.jxust.bigdata.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * 线程：为每一个客户端开启一个线程
 */
public class ServiceServerTask implements Runnable{
	Socket socket ;
	InputStream in=null;
	OutputStream out = null;
	
	public ServiceServerTask(Socket socket) {
		this.socket = socket;
	}

	//业务逻辑：跟客户端进行数据交互
	@Override
	public void run() {
		 try {
			//从socket连接中获取到与client之间的网络通信输入输出流 
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//从网络通信输入流中读取客户端发送过来的数据
			//注意：socketinputstream的读数据的方法都是阻塞的 
			String param = br.readLine();
			
			/*while(br.readLine()!=null){ 这样由于服务端一直在等待读，直到客户端关闭了流，而客户端的输入流在等待读服务端的数据，这样就互相等，会一直阻塞。如果发生阻塞，线程就不会关闭，当很多客户端时，就会造成资源浪费
				param = br.readLine();
				if(param.equals("end")) break;//可以这样 
			}*/
			
			
			System.out.println("服务端已收到！");
			
			/**
			 * 作业：
			 * 将以下业务调用逻辑写成更加通用的：可以根据客户端发过来的调用类名、调用方法名、调用该参数来灵活调用
			 * 
			 * 《反射》
			 * 
			 */
			
			GetDataServiceImpl getDataServiceImpl = new GetDataServiceImpl();
			String result = getDataServiceImpl.getData(param);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//将调用结果写到sokect的输出流中，以发送给客户端
			PrintWriter pw = new PrintWriter(out);
			pw.println(result);
			pw.flush();
			
			
			
		} catch (IOException e) {
			 
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
