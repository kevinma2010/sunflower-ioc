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
    private Map<String, String> dependencies = new HashMap<String, String>();
    
    public static BeanContext me() {
        return me;
        
    }

    public Map<String, Object> getBeans() {
        return beans;
    }

    public Map<String, String> getDependencies() {
        return dependencies;
    }
}
