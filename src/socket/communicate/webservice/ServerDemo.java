package socket.communicate.webservice;

import javax.xml.ws.Endpoint;

interface Business {
	public String sayHello(String message);
}

class BusinessImpl implements Business {

	@Override
	public String sayHello(String message) {
		if ("quit".equalsIgnoreCase(message.toString())) {
			System.out.println("Server will be shutdown!");
			System.exit(0);
		}
		System.out.println("Message from client: " + message);
		return "Server response:" + message;
	}
	
}

//http://blog.csdn.net/yangwenxue_admin/article/details/51059125
public class ServerDemo {
	
	// 发布 未测试
	static void published(){
		Endpoint.publish("http://localhost:8080/BusinessService", new BusinessImpl());
		System.out.println("Server has beed started");
	}
	
	public static void main(String[] args) {
		// 客户端代码 webservice-client
		HelloWorld hw = new HelloWorldImpl();
		// 浏览器 输入：http://192.168.1.7/vashon?wsdl
		Endpoint.publish("http://localhost/vashon", hw);
		System.out.println("Server has beed started");
	}

}
