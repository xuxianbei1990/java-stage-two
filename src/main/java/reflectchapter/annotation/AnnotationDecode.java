package reflectchapter.annotation;

import javax.annotation.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


@SpringBootApplication(scanBasePackages = "xxb")
public class AnnotationDecode {

    public static void main(String[] args) {
//		baseAnalyse();
        Class<AnnotationDecode> clazz = AnnotationDecode.class;
        if (clazz.isAnnotationPresent(SpringBootApplication.class)) {
            SpringBootApplication springBootApplication = clazz.getAnnotation(SpringBootApplication.class);
            System.out.println(springBootApplication);
            if (SpringBootApplication.class.isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = SpringBootApplication.class.getAnnotation(ComponentScan.class);
                System.out.println(Arrays.toString(componentScan.basePackages()));
            }

        }
    }

    private static void getAnnotation(Class<?> clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (filter(annotation)) {
                if (annotation.annotationType() == ComponentScan.class) {
                    ComponentScan componentScan = (ComponentScan) annotation;
                    System.out.println(Arrays.toString(componentScan.basePackages()));
                } else {
                    getAnnotation(annotation.annotationType());
                }
            }
        }
    }

    private static boolean filter(Annotation annotation) {
        return annotation.annotationType() != Deprecated.class &&
                annotation.annotationType() != SuppressWarnings.class &&
                annotation.annotationType() != Override.class &&
                annotation.annotationType() != PostConstruct.class &&
                annotation.annotationType() != PreDestroy.class &&
                annotation.annotationType() != Resource.class &&
                annotation.annotationType() != Resources.class &&
                annotation.annotationType() != Generated.class &&
                annotation.annotationType() != Target.class &&
                annotation.annotationType() != Retention.class &&
                annotation.annotationType() != Documented.class &&
                annotation.annotationType() != Inherited.class;
    }

    private static void baseAnalyse() {
        Class<?> cla = AnnotationBase.class;
        // 获取指定元素所有注解。注意：如果是类，只会返回类上的注解。而不会返回成员
        // 或者方法的注解
        Annotation[] annotations = cla.getAnnotations();

        // 判断该注解是否存在
        if (cla.isAnnotationPresent(ClassInfo.class)) {
            // 获取指定类型的注解类型 不过不存在返回null
            ClassInfo classInfo = cla.getAnnotation(ClassInfo.class);
            System.out.println(
                    classInfo.crated() + classInfo.createdBy() + classInfo.lastModified() + classInfo.revision());
        }

        //获取成员上的所有注解
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAnnotation.class)) {
                MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
                System.out.println("name: " + myAnnotation.name());
                System.out.println("getFieldValue: " + myAnnotation.getFieldValue());
                System.out.println("setFieldValue: " + myAnnotation.setFieldValue());
                System.out.println("realVale: " + myAnnotation.realVale());
            }
        }

        //获取成员方法上所有注解
        Method[] methods = cla.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Deprecated.class)) {
                Deprecated deprecated = method.getAnnotation(Deprecated.class);
                System.out.println("Method1 " + deprecated.getClass());
            } else if (method.isAnnotationPresent(BugsFixed.class)) {
                //如果没有 @Retention(RetentionPolicy.RUNTIME)
                System.out.println("Method2 " + BugsFixed.class);
            }
        }
    }

}
