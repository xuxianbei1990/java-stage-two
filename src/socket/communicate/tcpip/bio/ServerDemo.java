package socket.communicate.tcpip.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

// 简单服务端阻塞
public class ServerDemo {
	// 24
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(8080);
		// 接受客户端建立连接的请求，并返回socket对象， 以便和客户端进行交互
		// 通过Socket.getInputStream 和 Socket.getOutputStream 读写
		// 注意：此方法会一直阻塞到客户端发送建立连接请求，如果希望此方法
		// 最多阻塞一定的时间，则要在创建ServerSocket后调用
		// setSoTimeout(毫秒)
		while (true) {
			try {
				//如果有客户端连接过来会触发
				Socket socket = ss.accept();
				if (socket.isConnected()) {
					InputStream in = socket.getInputStream();
					System.out.println(IOUtils.toString(in));
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
