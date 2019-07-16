package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Convert number format.
 *
 * <li>write: It can be used on classes that inherit {@link Number}
 * <li>read: It can be used on classes {@link String}
 * 
 * @author zhuangjiaju
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ContentStyle {

    String fontName() default "宋体";

    short fontHeightInPoints() default (short)14;

    boolean bold() default true;

    IndexedColors indexedColors() default IndexedColors.WHITE1;
}
