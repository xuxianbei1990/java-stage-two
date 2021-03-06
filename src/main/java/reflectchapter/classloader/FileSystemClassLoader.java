package reflectchapter.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// 文件系统类加载器
public class FileSystemClassLoader extends ClassLoader {

	private String rootDir;

	public FileSystemClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}

	// 获取类的字节码
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);

		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			// /定义类型，一般在findClass方法中读取到对应字节码后调用，可以看出不可继承
			// （说明：JVM已经实现了对应的具体功能，解析对应的字节码，产生对应的内部数据结构放置到方法区，所以无需覆写，直接调用就可以了）
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		// 读取类文件的字节
		String path = classNameToPath(className);

		try {
			InputStream in = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			// 读取类文件的字节码
			while ((bytesNumRead = in.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}

			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String className) {
		// 得到类文件的完全路径
		return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
	}
}
