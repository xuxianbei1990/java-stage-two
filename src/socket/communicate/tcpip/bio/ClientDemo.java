package socket.communicate.tcpip.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 缺点：会生成大量的socket 而这些socket都会与服务端保持连接
 * 会有浪费大量连接资源，如果使用链接池，也会让客户端进入无限等待
 * 服务端：会有大量的线程  所以BIO连接数是有限的。
 */

//24
public class ClientDemo {

	/*
	 * 客户端写的例子
	 */
	public void simpleSocketWrite() throws IOException {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 8080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 向服务器发送字符串信息，要注意的是，此处即使写失败也不会爆出异常信息，
		// 并且一直会阻塞到写入操作系统或网络IO出现异常为止。
		PrintWriter out = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("helloworld");
		socket.close();
	}

	/*
	 * 客户端读取的例子
	 */
	public void simpleSocketRead() throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 8080);

		// 阻塞读取服务端的返回信息，以下代码会阻塞到服务端返回信息或网络IO出现
		// 异常为止，如果希望在超过一段时间后就不阻塞了，那么要在创建socket对象
		// 后调用socket.setSoTimeout()
		InputStream input = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		System.out.println(in.readLine());
		socket.close();
	}

	//多客户端写发送
	public void muliSocketWrite() throws UnknownHostException, IOException {
		for (int i = 0; i < 10; i++) {
			Socket socket = new Socket("127.0.0.1", 8080);
			OutputStream out = socket.getOutputStream();
			out.write(("socket number" + i).getBytes());
			socket.close();
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		ClientDemo cd = new ClientDemo();
		cd.muliSocketWrite();
	}
}
