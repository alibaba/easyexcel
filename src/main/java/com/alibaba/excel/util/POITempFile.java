package com.alibaba.excel.util;

import java.io.File;
import java.util.UUID;

/**
 *
 * @author jipengfei
 */
public class POITempFile {

    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private static final String POIFILES = "poifiles";

    private static final String CACHE = "excache";

    /**
     */
    public static void createPOIFilesDirectory() {
        createTmpDirectory(POIFILES);
    }

    public static File createCacheTmpFile() {
        File directory = createTmpDirectory(CACHE);
        return new File(directory.getPath(), UUID.randomUUID().toString());
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
            syncCreatePOIFilesDirectory(directory);
        }
        return directory;
    }

    /**
     *
     * @param directory
     */
    private static synchronized void syncCreatePOIFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
