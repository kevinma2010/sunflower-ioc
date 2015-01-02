package com.mlongbo.sunflower.ioc;

import com.mlongbo.sunflower.ioc.bean.Hi;
import org.junit.Test;

import java.util.Set;

/**
 * @author malongbo
 * @date 2014/12/31
 * @package com.mlongbo.sunflower.ioc
 */
public class AppTest {
    
    @Test
    public void test() {

        Set<Class<?>> classes = new Scanner().scanPackage("com.mlongbo.sunflower.ioc.bean");

        BeanContext.me().init(classes);

        Hi hi = (Hi) BeanContext.me().getBean("hi");
        hi.sayHello();
    }
}
