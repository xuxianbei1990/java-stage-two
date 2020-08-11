package reflectchapter.classdecode;

import java.util.List;

//测试类实体的
public class ClassTestModel {
	private int age;
	
	private String name;

	public int getAge() {
		return age;
	}
	
    private List<String> list;
	
	public void GodPunish() {
		System.out.println("无参数:" + "");
	}
	
	public void GodPunish(String KillName) {
		System.out.println(String.class + ":" + KillName);
	}
	
	public void GodPunish(Integer power) {
		System.out.println(Integer.class.toString() + ":" + power);
	}

	public int GodPunish(int power) {
		System.out.println(int.class.getName() + ":" + power);
		return power;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
}
