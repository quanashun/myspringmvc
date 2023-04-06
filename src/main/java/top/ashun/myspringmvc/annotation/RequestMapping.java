package top.ashun.myspringmvc.annotation;

import top.ashun.myspringmvc.enums.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 18483
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    public String value() default "";

    public String method() default RequestMethod.GET;

    public String path() default "";
}
