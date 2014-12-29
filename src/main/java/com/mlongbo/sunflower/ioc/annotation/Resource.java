package com.mlongbo.sunflower.ioc.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc.annotation
 */
@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
    
    String value() default "";
    
}
