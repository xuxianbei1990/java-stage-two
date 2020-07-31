package reflectchapter.classdecode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: xuxianbei
 * Date: 2020/7/31
 * Time: 11:00
 * Version:V1.0
 */
public class ClassConstruct {


    static class ConstructCreate {
        public ConstructCreate() {
            System.out.println("NoConstruct");
        }

        public ConstructCreate(Integer name) {
            System.out.println("ConstructCreate" + name);
        }

        public ConstructCreate(String name) {
            System.out.println("ConstructCreateStr" + name);
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = ConstructCreate.class;
        testConstructor(clazz);

    }

    private static void testConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                constructor.newInstance();
            } else {
                if (constructor.getParameters()[0].getType().isAssignableFrom(String.class) ) {
                    constructor.newInstance("constructor.getParameterCount()");
                } else {
                    constructor.newInstance(constructor.getParameterCount());
                }
            }
        }
    }
}
