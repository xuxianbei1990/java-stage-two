package socket.communicate.tcpip.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class ClientDemo {

	public static void main(String[] args) throws IOException {
		ClientDemo cd = new ClientDemo();
		cd.simpleSocketChannel();
	}

	public void simpleSocketChannel() throws IOException {
		// Socket socket = new Socket("localhost", 8080);
		// SocketAddress socketAddress = socket.getRemoteSocketAddress();
		SocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
		SocketChannel channel = SocketChannel.open();
		// 设置为非阻塞模式
		channel.configureBlocking(false);
		// 对于非阻塞模式，立刻返回false，表示连接正在建立中
		channel.connect(socketAddress);

		Selector selector = Selector.open();
		// 向channel注册selector以及连接事件
		channel.register(selector, SelectionKey.OP_CONNECT);

		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			if (channel.isConnected()) {
				String command = systemIn.readLine();
				channel.write(Charset.forName("UTF-8").encode(command));

				if (command == null || "quit".equalsIgnoreCase(command.trim())) {
					systemIn.close();
					channel.close();
					selector.close();
					System.out.print("Clinet quit !");
					System.exit(0);
				}
			}
			/*
			 * 阻塞至IO事件发生，或超时，如果希望一直等待，可以调用无参数 select方法。
			 */
			int nKeys = selector.select(100);
			SelectionKey skey = null;

			// 如nKeys大于0 说明有IO事件发生
			if (nKeys > 0) {
				Set<SelectionKey> keys = selector.selectedKeys();

				for (SelectionKey key : keys) {
					// 发生连接的事件
					if (key.isConnectable()) {
						SocketChannel sc = (SocketChannel) key.channel();
						sc.configureBlocking(false);
						// 注册IO读事件，通常不直接注册写事件，在发送缓冲未满的情况下，
						// 一直是可写的，如果注册写事件，而不写数据，容易造成CUP消耗100
						key = sc.register(selector, SelectionKey.OP_READ);
						// 完成连接的建立
						sc.finishConnect();
						// 有流可读取
					} else if (key.isReadable()) {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel) key.channel();
						int readBytes = 0;
						try {
							int ret = 0;
							try {
								// 读取目前可读的流，sc.read 返回的为成功复制到bytebuffer中的
								// 字节数，此步为阻塞操作，值可能为0； 当流结尾时候，返回-1
								while ((ret = sc.read(buffer)) > 0) {
									readBytes += ret;
								}
							} finally {
								buffer.flip();
							}
							if (readBytes > 0) {
								System.out.println(Charset.forName("UTF-8").decode(buffer).toString());
								buffer = null;
							}
						} finally {
							if (buffer != null) {
								buffer.clear();
							}
						}
					} // 可写入流
					else if (key.isWritable()) {
						// 取消对OP_Write事件的注册
						key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
						SocketChannel sc = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						// 此步为阻塞操作，返回成功的字节数， 当发送缓冲区已满，返回0；
						int writtenedSize = sc.write(buffer);
						// 如未写入，则继续注册OP_WRITE事件
						if (writtenedSize == 0) {
							key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
						}
					}
				}
			}
			selector.selectedKeys().clear();
		}
	}
}
