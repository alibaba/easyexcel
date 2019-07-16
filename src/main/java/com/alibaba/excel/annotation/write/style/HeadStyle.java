package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 配置
 * 
 * @author zhuangjiaju
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HeadStyle {
    String fontName() default "宋体";

    short fontHeightInPoints() default (short)14;

    boolean bold() default true;

    IndexedColors indexedColors() default IndexedColors.GREY_25_PERCENT;
}
