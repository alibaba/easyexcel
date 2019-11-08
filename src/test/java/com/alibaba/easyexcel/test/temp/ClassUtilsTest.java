package com.alibaba.easyexcel.test.temp;

import com.alibaba.excel.util.ClassUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassUtilsTest {

    @Test
    public void getBooleanFieldAlias() {
        final String active = ClassUtils.getBooleanFieldAlias("isActive");
        assertEquals("active", active);
    }
}
