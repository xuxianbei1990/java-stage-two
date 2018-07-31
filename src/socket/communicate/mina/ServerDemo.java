package socket.communicate.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

// 32
public class ServerDemo {

	/*
	 * IoAcceptor 负责服务端IO事件 线程池、消息发送、接收的filterChain
	 */
	public static void main(String[] args) throws IOException {
		final IoAcceptor acceptor = new NioSocketAcceptor();
		// 将发送对象序列化，以及接受字节流反序列化
		acceptor.getFilterChain().addLast("stringseriablize",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		IoHandler handler = new IoHandlerAdapter() {
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println(message);
			}
		};
		acceptor.bind(new InetSocketAddress(8080));
	}
}
