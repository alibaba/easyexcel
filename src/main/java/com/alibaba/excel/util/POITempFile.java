package com.alibaba.excel.util;

import java.io.File;

/**
 *
 * @author jipengfei
 */
public class POITempFile {

    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private static final String POIFILES = "poifiles";

    /**
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
     *
     * @param directory
     */
    private static synchronized void syncCreatePOIFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
