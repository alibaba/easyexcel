package com.alibaba.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Convert enum format.
 *
 * <p>
 * write: It can be used on classes {@link String} or {@link Integer}
 * <p>
 * read: It can be used on classes {@link String}
 *
 * @author xiajiafu
 * @since 2022/7/13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnumFormat {

    /**
     * the class of enum which to convert the java data or excel data.
     *
     * @return Format pattern
     */
    Class<? extends Enum<?>> targetClass();

    /**
     * the enum method which converts the java data to excel data.
     *
     * @return method name
     */
    String convertToExcelDataMethod() default "";

}
