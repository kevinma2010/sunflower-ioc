package com.mlongbo.sunflower.ioc;

import com.mlongbo.sunflower.ioc.annotation.Bean;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * @author malongbo
 * @date 2014/12/31
 * @package com.mlongbo.sunflower.ioc
 */
public class ScannerTest {
    
    @Test
    public void testScanClasses() {

        Set<Class<?>> classes = new Scanner().scanPackage("com.mlongbo.sunflower.ioc");

        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            Bean annotation = next.getAnnotation(Bean.class);
            if (annotation != null) {
                System.out.println(annotation.value());
                try {
                    BeanContext.me().put(annotation.value(), next.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(classes.size());
        System.out.println(BeanContext.me().getSize());
    }
}
