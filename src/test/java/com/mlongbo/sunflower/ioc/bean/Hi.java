package com.mlongbo.sunflower.ioc.bean;

import com.mlongbo.sunflower.ioc.annotation.Bean;
import com.mlongbo.sunflower.ioc.annotation.Resource;

/**
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc.bean
 */
@Bean("beanA")
public class Hi {
    @Resource
    private Person person;

    /**
     * Say Hello
     * @return
     */
    public void sayHello() {

        System.out.println("Hello " + person.getName());
        
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
