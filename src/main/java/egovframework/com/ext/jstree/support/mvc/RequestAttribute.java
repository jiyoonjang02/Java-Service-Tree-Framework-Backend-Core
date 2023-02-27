package egovframework.com.ext.jstree.support.mvc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestAttribute {
    String value();
}
