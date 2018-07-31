package socket.communicate.multicastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastListener {
	private int port;
	private String host;
	
	public MulticastListener(String host, int port) {
		this.port = port;
		this.host = host;
	}
	
	public void listen() {
		byte[] data = new byte[256];
		try {
			InetAddress ip = InetAddress.getByName(this.host);
			MulticastSocket ms = new MulticastSocket(this.port);
			// 加入组播， 如地址为非组播地址，则抛出IOException, 
			// ms.leaveGroup();移出组播地址
			ms.joinGroup(ip);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ms.receive(packet);
			String message = new String(packet.getData(), 0, packet.getLength());
			System.out.println(message);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 1234;
		String host = "224.1.1.1";
		MulticastListener ml = new MulticastListener(host, port);
		ml.listen();

	}

}
