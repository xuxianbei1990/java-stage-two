package socket.communicate.multicastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {
	private int port;
	private String host;
	private String data;
	
	public MulticastSender(String host, int port, String data) {
		this.port = port;
		this.host = host;
		this.data = data;
	}
	
	public void send() {
		try {
			InetAddress ip = InetAddress.getByName(this.host);
			DatagramPacket packet = new DatagramPacket(this.data.getBytes(),
					this.data.length(), ip, this.port);
			MulticastSocket ms = new MulticastSocket();
			ms.send(packet);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 1234;
		String host = "224.1.1.1";
		MulticastSender ml = new MulticastSender(host, port, "helloworld");
		ml.send();
	}

}
