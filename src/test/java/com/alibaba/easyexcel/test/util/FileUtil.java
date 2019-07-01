package com.alibaba.easyexcel.test.util;

import java.io.InputStream;

public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }
    
    public static String getPath() {
        return FileUtil.class.getResource("/").getPath();
    }
}
