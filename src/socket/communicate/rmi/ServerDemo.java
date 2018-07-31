package socket.communicate.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
 * 实际上是TCP/IP + BIO 通过序列化方式 
 * RMI 要求服务端的接口继承Remote接口，接口上的每种方法
 * 必须抛出RemoteException；
 * 
 * java RMI的缺点：
1，从代码中也可以看到，代码依赖于ip与端口
2，RMI依赖于Java远程消息交换协议JRMP（Java Remote Messaging Protocol），
该协议为java定制，要求服务端与客户端都为java编写
 */

interface Business extends Remote {
	String IPPORT = "BusinessDemo";
	public String sayHello(String message) throws RemoteException;
}

class BusinessImpl implements Business {

	@Override
	public String sayHello(String message) throws RemoteException {
		if ("quit".equalsIgnoreCase(message.toString())) {
			System.out.println("Server will be shutdown!");
			System.exit(0);
		}
		System.out.println("Message from client: " + message);
		return "Server response:" + message;
	}
}

public class ServerDemo {
	public static void main(String[] args) throws RemoteException {
		Business business = new BusinessImpl();
		UnicastRemoteObject.exportObject(business, 8080);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind(Business.IPPORT, business);
	}
}
