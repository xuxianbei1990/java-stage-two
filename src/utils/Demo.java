package utils;

class Student {
	
	public void outAge() {
		
	}
}

public class Demo {
	
	public static void main (String[] args) {
		Student student = new Student() {
			public void outAge() {
				System.out.println("123");;
			}
		};
		student.outAge();
	}

}
