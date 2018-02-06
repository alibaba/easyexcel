package com.alibaba.excel.util;

import java.io.File;

/**
 * 用于修复POI {@link org.apache.poi.util.DefaultTempFileCreationStrategy}在并发写，创建临时目录抛出异常的BUG。
 *
 * @author jipengfei
 */
public class EasyExcelTempFile {

    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private static final String POIFILES = "poifiles";

    private static final String EASY_EXCEL_FILES = "easyexcel";

    /**
     * 在创建ExcelBuilder后尝试创建临时目录，避免poi创建时候报错
     */
    public static void createPOIFilesDirectory() {

        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm property!");
        }
        File directory = new File(tmpDir, POIFILES);
        if (!directory.exists()) {
            syncCreatePOIFilesDirectory(directory);
        }

    }

    /**
     * 获取环境变量的配置
     * @return easyexcel临时目录
     */
    public static String getEasyExcelTmpDir() {
        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm property!");
        }
        File directory = new File(tmpDir, EASY_EXCEL_FILES);
        if (!directory.exists()) {
            syncCreatePOIFilesDirectory(directory);
        }
        return tmpDir + File.separator + EASY_EXCEL_FILES;
    }

    /**
     * 如果directory 不存在则创建
     *
     * @param directory
     */
    private static synchronized void syncCreatePOIFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
