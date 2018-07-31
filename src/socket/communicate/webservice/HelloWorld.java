package socket.communicate.webservice;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
	String sayHi(String name);

}
