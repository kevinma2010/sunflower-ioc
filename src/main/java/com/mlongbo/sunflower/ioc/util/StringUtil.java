package com.mlongbo.sunflower.ioc.util;

/**
 * @author malongbo
 * @date 15-1-2
 * @package com.mlongbo.sunflower.ioc.util
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
}
