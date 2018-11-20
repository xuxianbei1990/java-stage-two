package reflectchapter.classdecode;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 * getMethod 会获取到父类的方法 只获取public methods
 * 
 * getDeclaredMethods() 
          返回 Method 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的所有方法，
          包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
           
 */

public class ClassMethod {

    void testInvoke() throws Exception {
        Class<?> cla = ClassTestModel.class;

        Object obj = cla.newInstance();

        // 无参数
        Method m = cla.getMethod("GodPunish", new Class<?>[]{});
        m.invoke(obj, new Object[]{});

        // 字符串
        m = cla.getMethod("GodPunish", String.class);
        m.invoke(obj, "艾尔");

        // 基本类型
        m = cla.getMethod("GodPunish", int.class);
        Object o = m.invoke(obj, 1);
        System.out.println(o);

        // list
        List<String> list = new ArrayList<String>();
        list.add("sky");
        m = cla.getMethod("setList", List.class);
        m.invoke(obj, list);

        m = cla.getMethod("getList", new Class<?>[]{});
        o = m.invoke(obj, new Object[]{});
        Class<?> cls1 = m.getReturnType();
        System.out.println(cls1.equals(List.class));
        System.out.println("判断对象是否实现了List：" + (o instanceof List));
        // 判断某个class是否实现某个接口
        System.out.println("判断Class是否实现了List：" + List.class.isAssignableFrom(o.getClass()) + ":" + o);
        System.out.println("返回类型" + m.getReturnType());
        Type t = m.getGenericReturnType();
        System.out.println("返回泛型" + Class
                .forName(t.toString().substring((List.class.getName().length() + 1), t.toString().length() - 1)));

        // 获取所有方法
        Method[] ms = cla.getDeclaredMethods();
        StringBuilder sb = new StringBuilder();
        list.clear();
        for (Method m1 : ms) {
            sb.append(m1.getName());
            list.add(m1.getName());
        }
        System.out.println(list.contains("GodPunish"));
        System.out.println(sb);
    }

    /**
     * 对象拷贝 两个对象相同的属性进行赋值
     */
    void objCopy(Object source, Object dest) {
        Class<?> claSource = source.getClass();
        Class<?> claDest = dest.getClass();
        Method[] sourMethods = claSource.getDeclaredMethods();
        Method[] destMethods = claDest.getDeclaredMethods();
        for (Method destMethod : destMethods) {
            if (destMethod.getName().substring(0, 3).equals("set")) {
                for (Method sourMethod : sourMethods) {
                    if (sourMethod.getName().substring(0, 3).equals("get")
                            && sourMethod.getName().substring(3).equals(destMethod.getName().substring(3))) {
                        Object value = null;
                        try {
                            value = sourMethod.invoke(source, new Object[]{});
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        try {
                            destMethod.invoke(dest, value);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    private void methodAnnotationTest() {
        MethodAnnotationTest obj = new MethodAnnotationTestImpl();
        try {
            Method method = obj.getClass().getMethod("save", List.class);
            if (method.isAnnotationPresent(PostConstruct.class)) {
                System.out.println(method.invoke(obj, new ArrayList()));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ClassMethod classMethod = new ClassMethod();
        classMethod.methodAnnotationTest();
//		try {
//			classMethod.testInvoke();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        Student lulu = new Student();
        lulu.setAge(18);
        lulu.setBirthday(new Date());
        lulu.setMoney(new BigDecimal(100.22));
        lulu.setName("仙灵女巫露露");

        Hero hero = new Hero();
        classMethod.objCopy(lulu, hero);
        lulu.setAge(998);
        lulu.setMoney(new BigDecimal(1089898.22));
        lulu.setName("贝亚娜露露");
        System.out.println(lulu);
        System.out.println(hero);
        // ClassTestModel ctm = new ClassTestModel();
        // ctm.setAge(18);
        // ctm.setName("申世景");
        // Class cla = ClassTestModel.class;
        // try {
        // Method method = cla.getMethod("getAge", Integer.class);
        // Object obj = method.invoke(ctm, new Object());
        // } catch (NoSuchMethodException | SecurityException |
        // IllegalAccessException | IllegalArgumentException
        // | InvocationTargetException e) {
        // e.printStackTrace();
        // }
    }

}

class Hero {
    private String name;
    private Integer age;
    private String Kill = "邪神怖拉修";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getKill() {
        return Kill;
    }

    public void setKill(String kill) {
        Kill = kill;
    }

    @Override
    public String toString() {
        return name + age;
    }

}

class Student {
    private String name;
    private Integer age;
    private BigDecimal money;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--姓名：").append(name).append("--age:").append(age).append("--身价:")
                .append(money.doubleValue()).append("--生日:").append(birthday);
        return sb.toString();
    }
}
