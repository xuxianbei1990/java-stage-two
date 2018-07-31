package xmlchapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
//http://blog.csdn.net/redarmy_chen/article/details/12969219
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

enum ReadType {
	SAXREADER, PARSETEXT, CREATEDOCUMENT;
};

public class Dom4jDemo {
	public static void main(String[] args) {
		Dom4jDemo dd = new Dom4jDemo();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read("resources/KingOfFighter.xml");
			Element element = document.getRootElement();
			dd.ergodicText(element);
			dd.ergodicAllNode(element);
//			 element = element.elementIterator().next();
//			 dd.listNodes(element);
//			for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
//				Element ele = it.next();
//				System.out.println(ele.getText());
//			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//遍历所有节点
	public void ergodicAllNode(Element element){
		System.out.println("--------------------");  
	      
	    //当前节点的名称、文本内容和属性  
	    System.out.println("当前节点名称："+element.getName());//当前节点名称  
	    System.out.println("当前节点的内容："+element.getTextTrim());//当前节点名称  
	    List<Attribute> listAttr=element.attributes();//当前节点的所有属性的list  
	    for(Attribute attr:listAttr){//遍历当前节点的所有属性  
	        String name=attr.getName();//属性名称  
	        String value=attr.getValue();//属性的值  
	        System.out.println("属性名称："+name+"属性值："+value);  
	    }  
	      
	    //递归遍历当前节点所有的子节点  
	    List<Element> listElement=element.elements();//所有一级子节点的list  
	    for(Element e:listElement){//遍历所有一级子节点  
	        this.ergodicAllNode(e);//递归  
	    }  
		
	}
	
	//遍历某个节点下面player节点
	public void ergodicText(Element element) {
		List<Element> eles = element.elements("player");
		for (Element ele : eles) {
			System.out.println(ele.getName() + ele.attributeValue("Name"));
		}
	}

	// 参考代码
	public void listNodes(Element node) {
		System.out.println("当前节点的名称：：" + node.getName());
		// 获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attr : list) {
			System.out.println(attr.getText() + "-----" + attr.getName() + "---" + attr.getValue());
		}

		if (!(node.getTextTrim().equals(""))) {
			System.out.println("文本内容：：：：" + node.getText());
		}

		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			listNodes(e);
		}
	}

	// 获取Document 4种方式
	Document getDocumentFunc(ReadType readType) {
		Document document = null;
		try {
			if (readType == ReadType.SAXREADER) {
				SAXReader reader = new SAXReader();
				document = reader.read("resources/KingOfFighter.xml");
			} else if (readType == ReadType.PARSETEXT) {
				String text = "<csdn> <java>Java班</java></csdn>";
				document = DocumentHelper.parseText(text);
			} else if (readType == ReadType.CREATEDOCUMENT) {
				document = DocumentHelper.createDocument();
				// 创建根节点
				Element root = document.addElement("csdn");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return document;
	}

	// 节点对象操作的方法
	void nodeOperator(Document document) {
		// 获取文档的根节点.
		Element root = document.getRootElement();
		// 取得某个节点 取第一匹配的节点
		Element element = root.element("库拉");
		// 取得节点的值
		String name = element.getText();

		System.out.println(name);

		// 取得某节点下所有名为LuLu的子节点。
		List<Element> elements = root.elements("player");

		// 遍历root下所有节点
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element ele = it.next();
			System.out.println(ele.getText());
		}

		// 在某节点下添加子节点
		Element elm = element.addElement("97");
		// 设置节点文字
		elm.setText("大蛇");
		// 移除某个节点， 下面应该会报错
		element.remove(elm);
		Element contentEle = elm.addElement("content");
		contentEle.addCDATA("cdata区域");

	}

	// 节点对象属性方法操作
	void attribute(Element element) {
		// 取得某节点的某属性
		Attribute attribute = element.attribute("ID");
		// 取得属性的文字
		String text = attribute.getText();
		// 删除谋属性
		element.remove(attribute);
		// 添加属性和文字
		element.addAttribute("八神", "苍炎");

	}

	// document 另存为
	void documentToFile(Document document) {
		try {
			// 全英文
			XMLWriter writer = new XMLWriter(new FileWriter("ot.xml"));
			writer.write(document);
			writer.close();

			// 文档如果有中文
			// 创建文件输出的时候，自动缩进的格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileWriter("output.xml"), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 字符串和XML相互转换
	void StringExchangeXML() throws DocumentException {
		// 解析文字
		String text = "<csdn> <java>Java班</java></csdn>";
		Document document = DocumentHelper.parseText(text);

		// xml到字符串
		SAXReader reader = new SAXReader();
		document = reader.read(new File("csdn.xml"));
		Element root = document.getRootElement();
		String docXmlText = document.asXML();
		String rootXmlText = root.asXML();
		Element ele = root.addElement("csdn");
		text = ele.asXML();

	}
}
