package com.webserver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 用于处理客户端请求
 * @author soft01
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try{
			// 1 解析请求
			// 实例化请求对象,实例化过程也是解析请求的过程
			HttpRequest request = new HttpRequest(socket);
			// 实例化响应对象
			HttpResponse response = new HttpResponse(socket);
			
			// 2 处理请求
			// 先通过request获取用户请求的资源的抽象路径
			String path = request.getUrl();
			// 从webapps目录下根据该路径寻找请求资源
			File file = new File("./webapps"+path);
			if (file.exists()){
				System.out.println("该资源已找到");
				// 将要响应的资源设置到response中
				response.setEntity(file);
				// 响应客户端
				response.flush();
			}else{
				System.out.println("该资源不存在");
			}
			
			// 3 发送响应
			
		
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				// 处理完毕后与客户端断开释放资源
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
