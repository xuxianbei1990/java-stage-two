package socket.communicate.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientDemo {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost");
		Business business = (Business) registry.lookup("BusinessDemo");
		System.out.print(business.sayHello("123"));
	}
}
