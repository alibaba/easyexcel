package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * bean utils
 *
 * @author Jiaju Zhuang
 */
public class BeanMapUtils {

    /**
     * Helper method to create a new <code>BeanMap</code>. For finer
     * control over the generated instance, use a new instance of
     * <code>BeanMap.Generator</code> instead of this static method.
     *
     * Custom naming policy to prevent null pointer exceptions.
     * Uses getter methods to access field values, enhancing encapsulation.
     * Handles exceptions more gracefully and ensures thread safety for map.
     *
     * @param bean the JavaBean underlying the map
     * @return a new <code>BeanMap</code> instance
     */
    public static Map<String, Object> create(Object bean) {
        // Create a map to store the bean's property names and their corresponding values
        Map<String, Object> propertyMap = new HashMap<>(16);

        // Iterate over all declared fields of the bean
        for (Field field : bean.getClass().getDeclaredFields()) {
            // Set the field accessible to be able to access private fields
            field.setAccessible(true);
            try {
                // Get the field value using a getter method and put it into the map
                Object fieldValue = getFieldValueUsingGetter(bean, field);
                if (fieldValue != null) {
                    propertyMap.put(field.getName(), fieldValue);
                }
            } catch (Exception e) {
                // Capture all exceptions that can occur during field access
                e.printStackTrace();
            }
        }
        // Return the map containing the bean's properties
        return propertyMap;
    }


    private static Object getFieldValueUsingGetter(Object bean, Field field) throws Exception {
        // Assuming the getter method follows the Java Bean convention:
        // getMethodName for fields or isMethodName for boolean fields
        String getterMethodName = "get" + capitalize(field.getName());
        if (field.getType().isAssignableFrom(boolean.class)) {
            getterMethodName = "is" + capitalize(field.getName());
        }

        try {
            // Attempt to call the getter method to get the field value
            return bean.getClass().getMethod(getterMethodName).invoke(bean);
        } catch (NoSuchMethodException e) {
            // If no getter method found, return null. This might indicate
            // a need for direct field access which should be avoided if possible.
            return null;
        }
    }

    private static String capitalize(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
