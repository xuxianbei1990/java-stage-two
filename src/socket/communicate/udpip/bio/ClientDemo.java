package socket.communicate.udpip.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// 28  
/**
 * 由于UDP/IP 无连接的，如果希望双向通信，就必须启动一个监听端口 绑定端口，
 * 
 * @author xuxb
 *
 */
public class ClientDemo {

	public static void main(String[] args) throws IOException {
		String mes = "你好！我是客户！";
		byte[] buf = mes.getBytes();

		InetAddress server = InetAddress.getLocalHost();

		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, server, 8080);
		// 阻塞发送packet 到指定的服务器和端口，当出现网络IO异常抛出IOException
		// 当连不上目标地址和端口时， 抛出PortUnreachableException
		socket.send(packet);
		socket.close();

		// 负责监听端口
		DatagramSocket serverSocket = new DatagramSocket(8081);
		/*
		 * 一个 DatagramPacket 实例中所允许传输的最大数据量为 65507 个字节， 也即是 UDP 数据报文所能负载的最多数据。
		 * 因此，可以用一个 65600 字节左右的缓存数组来接受数据。
		 */
		byte[] buffer = new byte[65507];
		// 负责通信
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		// 阻塞并同步读取流信息，如果接受信息超过 receivePacket则删除。
//		serverSocket.setSoTimeout(100);
		serverSocket.receive(receivePacket);
		
		String receiveMsg = new String(buffer, 0, receivePacket.getLength());
		System.out.println("客户方接受方返回的消息：" + receiveMsg);
		
		serverSocket.close();
	}

}
