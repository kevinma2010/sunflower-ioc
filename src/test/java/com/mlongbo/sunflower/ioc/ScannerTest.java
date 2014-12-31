package com.mlongbo.sunflower.ioc;

import com.mlongbo.sunflower.ioc.annotation.Bean;
import com.mlongbo.sunflower.ioc.annotation.Resource;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
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
                String beanName = annotation.value();
                System.out.println();
                try {
                    BeanContext.me().getBeans().put(annotation.value(), next.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Field[] fields = next.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    Resource fieldAnnotation = field.getAnnotation(Resource.class);
                    if (fieldAnnotation != null) {
                        String resourceName = fieldAnnotation.value().equals("") ? field.getName() : fieldAnnotation.value();
                        System.out.println("resource: " + resourceName);
                        BeanContext.me().getDependencies().put(beanName.concat(".").concat(field.getName()), resourceName);
                    }

                }
            }
        }
        Map<String, String> dependencies = BeanContext.me().getDependencies();
        System.out.println(BeanContext.me().getDependencies().size());

        Iterator<Map.Entry<String, String>> entryIterator = dependencies.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> next = entryIterator.next();
            String key = next.getKey();
            String value = next.getValue();
            String[] split = key.split("\\.");
            try {
                PropertyUtils.setProperty(BeanContext.me().getBeans().get(split[0]), split[1], BeanContext.me().getBeans().get(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> beans = BeanContext.me().getBeans();
        System.out.println();
    }
}
