package socket.communicate.udpip.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

// 29
public class ClientDemo {
	public static void main(String[] args) throws IOException {
		DatagramChannel sendChannel = DatagramChannel.open();
		sendChannel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress("localhost", 8080);
		sendChannel.connect(target);
		sendChannel.write(ByteBuffer.wrap("the King of Fighter".getBytes()));
		
		DatagramChannel receiveChannel = DatagramChannel.open();
		receiveChannel.configureBlocking(false);
		DatagramSocket socket = receiveChannel.socket();
		socket.bind(new InetSocketAddress(8081));
		Selector selector = Selector.open();
		receiveChannel.register(selector, SelectionKey.OP_READ);

		while (selector.select() > 0) {
			System.out.println("新channel加入");

			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

			readAndSend(receiveChannel, iterator);
		}
		
	}

	public static void readAndSend(DatagramChannel receiveChannel, Iterator<SelectionKey> iterator) throws IOException {
		while (iterator.hasNext()) {
			SelectionKey key = null;

			key = (SelectionKey) iterator.next();
			iterator.remove();

			if (key.isReadable()) {
				DatagramChannel sc = (DatagramChannel) key.channel();
				String content = "";
				ByteBuffer buf = ByteBuffer.allocate(1024);
				buf.clear();
				SocketAddress address = sc.receive(buf);
				// 调用ByteBuffer.flip()方法是因为在向ByteBuffer写入数据后，position为
				// 缓冲区中刚刚读入的数据的最后一个字节的位置，flip方法将limit值置
				// 为position值，position置0，这样在调用get*()方法从ByteBuffer中
				// 取数据时就可以取到ByteBuffer中的有效数据
				buf.flip(); // make buffer ready for read

				while (buf.hasRemaining()) {
					buf.get(new byte[buf.limit()]); // read 1 byte at a time
					content += new String(buf.array());
				}
				buf.clear();
				System.out.println("接收" + content.trim());

				ByteBuffer buf2 = ByteBuffer.allocate(65507);
				buf2.clear();
				buf2.put("the sky is mine 世界".getBytes());
				buf2.flip();
				// 往服务端发送，这个通道是服务端绑定的
//				receiveChannel.send(buf2, new InetSocketAddress("localhost", 8080));
			} else if (key.isWritable()) {

			}
			// 异常执行下面代码
			// if (key != null) {
			// key.cancel();
			// key.channel().close();
			// }

		}
	}

}
