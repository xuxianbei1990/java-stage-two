package collection.chapter;

import java.util.HashMap;

public class HashMapDemo {

	private static class Person {
		int idCard;
		String name;
		
		public Person(int idCard, String name) {
			this.idCard = idCard;
			this.name = name;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			
			Person person = (Person) o;
			
			return this.idCard == person.idCard;
		}
		//以下代码注释不注释，效果不一样    两个对象值相同,有相同的hash code
//		@Override 
//		public int hashCode() {
//			Integer id = idCard;
//			return id == null ? 0 : id.hashCode();
//			
//		}
		
	}
	
	public static void main(String[] args) {
		HashMap<Person, String> map = new HashMap<Person, String>();
		Person person = new Person(1234, "乔峰");
		System.out.println(person.hashCode());
		map.put(person, "天龙八部");
		person = new Person(1234, "乔峰");
		System.out.println(person.hashCode());
		System.out.println("结果：" + map.get(person));
	}

}
