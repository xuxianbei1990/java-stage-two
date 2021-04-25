package reflectchapter.annotation;

import java.lang.annotation.*;

/**
 * @author: xuxianbei
 * Date: 2021/4/25
 * Time: 14:37
 * Version:V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan
public @interface SpringBootApplication {

    String[] scanBasePackages() default {};
}
