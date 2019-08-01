package com.alibaba.excel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelGenerateException;

/**
 *
 * @author jipengfei
 */
public class FileUtils {

    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private static final String POIFILES = "poifiles";

    private static final String CACHE = "excache";
    private static final int WRITE_BUFF_SIZE = 8192;

    /**
     * Write inputStream to file
     *
     * @param file
     * @param inputStream
     */
    public static void writeToFile(File file, InputStream inputStream) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[WRITE_BUFF_SIZE];
            while ((bytesRead = inputStream.read(buffer, 0, WRITE_BUFF_SIZE)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new ExcelAnalysisException("Can not create temporary file!", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new ExcelAnalysisException("Can not close 'outputStream'!", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ExcelAnalysisException("Can not close 'inputStream'", e);
                }
            }
        }
    }

    /**
     */
    public static void createPoiFilesDirectory() {
        createTmpDirectory(POIFILES);
    }

    public static File createCacheTmpFile() {
        File directory = createTmpDirectory(CACHE);
        File cache = new File(directory.getPath(), UUID.randomUUID().toString());
        if (!cache.mkdir()) {
            throw new ExcelGenerateException("Can not create temp file!");
        }
        return cache;
    }

    /**
     * delete file
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    public static File createTmpDirectory(String path) {
        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm property!");
        }
        File directory = new File(tmpDir, path);
        if (!directory.exists()) {
            syncCreatePoiFilesDirectory(directory);
        }
        return directory;
    }

    /**
     *
     * @param directory
     */
    private static synchronized void syncCreatePoiFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
