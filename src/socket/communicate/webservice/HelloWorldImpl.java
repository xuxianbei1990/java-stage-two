package socket.communicate.webservice;

import java.util.Date;

import javax.jws.WebService;

//指定webservice所实现的接口以及服务名称  
@WebService(endpointInterface="socket.communicate.webservice.HelloWorld", serviceName="HelloWorldWs")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHi(String name) {
		return name + "你好，然后消失吧" + new Date();
	}

}
