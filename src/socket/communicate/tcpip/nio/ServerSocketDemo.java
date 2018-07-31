package socket.communicate.tcpip.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import sun.nio.cs.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class ServerSocketDemo {
	public static void main(String[] args) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket serverSocket = ssc.socket();
		serverSocket.bind(new InetSocketAddress(8080));
		ssc.configureBlocking(false);
		Selector select = Selector.open();
		ssc.register(select, SelectionKey.OP_ACCEPT);

		while (true) {
  			int nKeys = select.select(100);
			if (nKeys > 0) {
				Set<SelectionKey> keys = select.selectedKeys();
				for (SelectionKey key : keys) {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel sc = server.accept();
						if (sc == null)
							continue;
						sc.configureBlocking(false);
						sc.register(select, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						// 分配一个新的字节缓冲区。
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel) key.channel();
						int readBytes = 0;
						String message = null;
						try {
							int ret;
							try {
								while ((ret = sc.read(buffer)) > 0) {
									readBytes += ret;
								}
							} catch (Exception e) {
								readBytes = 0;
							} finally {
								// 反转此缓冲区， 首先对当前位置设置限制，然后将该位置
								// 为0；
								buffer.flip();
							}
							if (readBytes > 0) {
								message = Charset.forName("UTF-8").decode(buffer).toString();
								buffer = null;
							}
						} finally {
							if (buffer != null)
								buffer.clear();
						}
						if (readBytes > 0) {
							System.out.println("message from client:" + message);
							if ("quit".equalsIgnoreCase(message.trim())){
								sc.close();
								select.close();
								System.out.println("Server has been shutdown!");
								System.exit(0);
							}
							String outMessage = "server response:" + message;
							sc.write(Charset.forName("UTF-8").encode(outMessage));
						}
					}
				}
				select.selectedKeys().clear();
			}
		}
	}

}
