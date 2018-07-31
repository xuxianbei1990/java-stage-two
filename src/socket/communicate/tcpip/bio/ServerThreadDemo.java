package socket.communicate.tcpip.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

/*
 * 服务端多线程例子。
 * 每次新增一个客户端，增加一个线程处理
 */

public class ServerThreadDemo {

	static final int PORT = 8080;
	
	public static void main(String[] args) {
		new ServerThreadDemo().init();
	}
	
	class HandlerThread implements Runnable {

		private Socket socket;
		
		public HandlerThread(Socket client) {
			this.socket = client;
			new Thread(this).start();
		}
		
		@Override
		public void run() {
			try {
				InputStream in = socket.getInputStream();
				System.out.println("客户端发送过来的内容" + IOUtils.toString(in));
				in.close();
			} catch(IOException e) {
				System.out.println("服务器 run 异常" + e.getMessage());
			} finally {
				if (socket != null) {
					try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("服务端 finally 异常:" + e.getMessage());    
                    } 
				}
			}
		}
		
	}
	
	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
				Socket socket = serverSocket.accept();
				new HandlerThread(socket);
			}
		} catch (IOException e) {
			System.out.println("服务器异常 " + e.getMessage());
		}
	}
}
