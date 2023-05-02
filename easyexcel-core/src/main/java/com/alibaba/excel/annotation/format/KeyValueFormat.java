package com.alibaba.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Convert key&value format.
 *
 * <p>
 * write: It can be used on classes {@link String} or {@link Integer}
 * <p>
 * read: It can be used on classes {@link String} or {@link Integer}
 *
 * @author xiajiafu
 * @since 2022/7/13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KeyValueFormat {

    /**
     * the class which converts java data or excel data.
     *
     * @return class
     */
    Class<?> targetClass();

    /**
     * the method which converts excel data to java data.
     *
     * @return method name
     */
    String javaify() default "";

    /**
     * the method which converts java data to excel data.
     *
     * @return method name
     */
    String excelify() default "";

}
