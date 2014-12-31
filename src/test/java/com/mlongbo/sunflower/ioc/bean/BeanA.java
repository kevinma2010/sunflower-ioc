package com.mlongbo.sunflower.ioc.bean;

import com.mlongbo.sunflower.ioc.annotation.Bean;
import com.mlongbo.sunflower.ioc.annotation.Resource;

/**
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc.bean
 */
@Bean("beanA")
public class BeanA {
    @Resource
    private BeanB beanB;

    /**
     * Say Hello*
     * @return
     */
    public String sayHello() {
        
        return "Hello " + beanB.getName();
        
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
