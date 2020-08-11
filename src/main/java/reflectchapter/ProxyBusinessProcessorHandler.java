package reflectchapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//业务代理类  Proxy:代理
public class ProxyBusinessProcessorHandler implements InvocationHandler {

	private Object target = null;
	private List<Method> methods;
	private List<Method> history;

	ProxyBusinessProcessorHandler(Object target) {
		this.target = target;
		methods = new ArrayList<Method>();
		history = Collections.unmodifiableList(methods);
	}

	public static synchronized Object proxyFor(Object obj) {
		Class<?> objClass = obj.getClass();
		return Proxy.newProxyInstance(objClass.getClassLoader(), objClass.getInterfaces(),
				new ProxyBusinessProcessorHandler(obj));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("You can do something here before process your business");
		Object result = method.invoke(target, args);
		System.out.print("You can do something here after process your business");
		return result;
	}

	public List<Method> getHistory() {
		return history;
	}

}
