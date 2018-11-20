package reflectchapter.classdecode;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * User: xuxb
 * Date: 2018-10-16
 * Time: 17:02
 * Version:V1.0
 */
public class MethodAnnotationTestImpl implements MethodAnnotationTest {
    @Override
    @PostConstruct
    public boolean save(List<String> list) {
        return false;
    }
}
