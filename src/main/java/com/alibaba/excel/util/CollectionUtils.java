package com.alibaba.excel.util;

import java.util.Collection;
import java.util.Map;

/**
 * Collection utils
 * 
 * @author jipengfei
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
