package com.mlongbo.sunflower.ioc.bean;

import com.mlongbo.sunflower.ioc.annotation.Bean;

/**
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc.bean
 */
@Bean("beanB")
public class BeanB {

    private String name = "Longbo Ma";

    public String getName() {
        return name;
    }
}
