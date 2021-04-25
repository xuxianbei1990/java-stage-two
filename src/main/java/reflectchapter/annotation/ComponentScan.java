package reflectchapter.annotation;

import java.lang.annotation.*;

/**
 * @author: xuxianbei
 * Date: 2021/4/25
 * Time: 14:37
 * Version:V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

    String[] basePackages() default {"xxb"};
}
