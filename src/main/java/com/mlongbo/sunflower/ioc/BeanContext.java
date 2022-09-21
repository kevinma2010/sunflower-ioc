package com.mlongbo.sunflower.ioc;

import com.mlongbo.sunflower.ioc.annotation.Bean;
import com.mlongbo.sunflower.ioc.annotation.Resource;
import com.mlongbo.sunflower.ioc.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author malongbo
 * @date 2014/12/31
 * @package com.mlongbo.sunflower.ioc
 */
public class BeanContext {
    private static final BeanContext me = new BeanContext();//单例对象
    private boolean isInitialized =  false;//是否已初始化

    //存放bean
    private final Map<String, Object> beans = new HashMap<String, Object>();
    //记录依赖关系
    private final Map<String, String> dependencies = new HashMap<String, String>();

    /**
     * 获取实体对象
     * @return BeanContext Instance
     */
    public static BeanContext me() {

        return me;
    }

    /**
     * 获取bean对象
     * @param name beanName
     * @return
     */
    public Object getBean(String name) {

        return beans.get(name);
    }
    /**
     * 初始化各对象及依赖
     * @param classes 扫描到的类集合
     */
    public void init(Set<Class<?>> classes) {
        if (isInitialized || classes == null || classes.size() == 0) {
            return;
        }

        //创建bean,扫描依赖关系
        this.createBeansAndScanDependencies(classes);

        //注入依赖
        this.injectBeans();

        this.isInitialized = true;
    }

    /**
     * 扫描注解,创建对象,记录依赖关系
     * @param classes 类集合
     */
    private void createBeansAndScanDependencies(Set<Class<?>> classes) {

        for (Class<?> item : classes) {
            Bean annotation = item.getAnnotation(Bean.class);
            if (annotation != null) {
                String beanName = annotation.value();
                try {
                    this.beans.put(annotation.value(), item.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                /*
                记录依赖关系
                 */
                Field[] fields = item.getDeclaredFields();

                for (Field field : fields) {
                    Resource fieldAnnotation = field.getAnnotation(Resource.class);
                    if (fieldAnnotation != null) {
                        //获取依赖的bean的名称,如果为null,则使用字段名称
                        String resourceName = fieldAnnotation.value();
                        if (StringUtil.isEmpty(resourceName)) {
                            resourceName = field.getName();
                        }

                        this.dependencies.put(beanName.concat(".").concat(field.getName()), resourceName);
                    }

                }
            }
        }
    }
    /**
     * 扫描依赖关系并注入bean
     */
    private void injectBeans() {

        for (Map.Entry<String, String> item : dependencies.entrySet()) {
            String key = item.getKey();
            String value = item.getValue();//依赖对象的值
            String[] split = key.split("\\.");//数组第一个值表示bean对象名称,第二个值为字段属性名称
            try {
                PropertyUtils.setProperty(beans.get(split[0]), split[1], beans.get(value));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
