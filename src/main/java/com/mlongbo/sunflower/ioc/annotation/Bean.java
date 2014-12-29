package com.mlongbo.sunflower.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc.annotation
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    
    String value();
    
}
