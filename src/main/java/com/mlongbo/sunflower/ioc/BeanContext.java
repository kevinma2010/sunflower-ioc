package com.mlongbo.sunflower.ioc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author malongbo
 * @date 2014/12/31
 * @package com.mlongbo.sunflower.ioc
 */
public class BeanContext {
    private static BeanContext me = new BeanContext();
    
    private Map<String, Object> beans = new HashMap<String, Object>();
    
    public void put(String name, Object bean) {
        beans.put(name, bean);
    }
    
    public static BeanContext me() {
        return me;
        
    }
    
    public Object get(String name) {
        return beans.get(name);
        
    }
    public int getSize() {
        return beans.size();
        
    }
}
