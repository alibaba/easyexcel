package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class AnnotationUtilsTest {

    @Test
    public void inherited() {
        HeadRowHeight direct = AnnotatedElementUtils.findMergedAnnotation(Direct.class, HeadRowHeight.class);
        Assert.assertEquals(direct.value(), 100);
        HeadRowHeight composition = AnnotatedElementUtils.findMergedAnnotation(CompositionC.class, HeadRowHeight.class);
        Assert.assertEquals(composition.value(), 200);
    }

    @Composition
    @Data
    public static class CompositionC {
        @ExcelProperty("a")
        private String a;
    }

    @Composition
    @HeadRowHeight(100)
    @Data
    public static class Direct {
        @ExcelProperty("a")
        private String a;
    }


    @HeadRowHeight(200)
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface Composition {

    }
}
