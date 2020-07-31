package collection.chapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * 让其他两个对象可以对比
 */
class comparatorTest implements Comparator<comparableTest> {

    @Override
    public int compare(comparableTest o1, comparableTest o2) {
        return o1.getAge() > o2.getAge() ? -1 : (o1.getAge() == o2.getAge() ? 0 : 1);
    }

}

/*
 * Comparable 让该对象拥有排序能力。
 */
class comparableTest implements Comparable<comparableTest> {

    private int age;

    comparableTest(int age) {
        this.setAge(age);
    }

    @Override
    public String toString() {
        return age + "";
    }

    @Override
    public int compareTo(comparableTest o) {
        return this.age > o.age ? 1 : (this.age == o.age ? 0 : -1);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

public class ComparableDemo {

    public static void main(String[] args) {
        List<comparableTest> list = new ArrayList<comparableTest>();
        for (int i = 0; i < 10; i++) {
            list.add(new comparableTest(i));
        }
        list.sort(null);
        System.out.println(list.toString());

        comparatorTest ct = new comparatorTest();
        list.sort(ct);
        System.out.println("comparatorTest" + list.toString());

        List<Integer> listInt = new ArrayList<Integer>();
        for (int i = 10; i > 0; i--)
            listInt.add(Integer.valueOf(i));
        listInt.sort(null);
        System.out.println("listInt" + listInt.toString());
    }

}
