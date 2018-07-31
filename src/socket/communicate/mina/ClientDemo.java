package socket.communicate.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/*
 * IoConnector 客户端 消息处理器、IO事件处理线程池、消息发送、接受的Filter Chain
 * IoHandler 连接事件、IO事件或异常事件
 * IoSession 类似SocketChannel封装，可以更多连接的控制以及信息的输出
 */

public class ClientDemo {

	public static void main(String[] args) {
		
		// 创建一个线程池大小为CPU核数 +1 的SocketConnector对象
		//Runtime.getRuntime().availableProcessors() + 1,
		// Executors.newCachedThreadPool()
		SocketConnector  ioConnector = new NioSocketConnector();
		// 设置TCP NoDelay 为true
		ioConnector.getSessionConfig().setTcpNoDelay(true);
		// 将发送对象序列化，以及接受字节流反序列化
		ioConnector.getFilterChain().addLast("stringserizlize", 
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		
		// IoHandler ：连接事件、IO事件或异常事件
		IoHandler handler = new IoHandlerAdapter() {
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println(message);
			}
		};
		ioConnector.setHandler(handler);
		ioConnector.setConnectTimeoutMillis(1000);
		ConnectFuture connectFutrue = ioConnector.connect(new InetSocketAddress("localhost", 8080));
		
		IoSession session = connectFutrue.getSession();
		session.write("sky");
	}
}
