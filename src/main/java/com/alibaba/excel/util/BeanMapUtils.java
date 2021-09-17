package com.alibaba.excel.util;

import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.beans.BeanMap.Generator;
import net.sf.cglib.core.DefaultNamingPolicy;

/**
 * bean utils
 *
 * @author Jiaju Zhuang
 */
public class BeanMapUtils {

    /**
     * Helper method to create a new <code>BeanMap</code>.  For finer
     * control over the generated instance, use a new instance of
     * <code>BeanMap.Generator</code> instead of this static method.
     *
     * Custom naming policy to prevent null pointer exceptions.
     * see: https://github.com/alibaba/easyexcel/issues/2064
     *
     * @param bean the JavaBean underlying the map
     * @return a new <code>BeanMap</code> instance
     */
    public static BeanMap create(Object bean) {
        Generator gen = new Generator();
        gen.setBean(bean);
        gen.setNamingPolicy(EasyExcelNamingPolicy.INSTANCE);
        return gen.create();
    }

    public static class EasyExcelNamingPolicy extends DefaultNamingPolicy {
        public static final EasyExcelNamingPolicy INSTANCE = new EasyExcelNamingPolicy();

        @Override
        protected String getTag() {
            return "ByEasyExcelCGLIB";
        }
    }
}
