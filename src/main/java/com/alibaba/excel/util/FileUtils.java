package com.alibaba.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private FileUtils() {}

    /**
     * Reads the contents of a file into a byte array. * The file is always closed.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readFileToByteArray(final File file) throws IOException {
        InputStream in = openInputStream(file);
        try {
            final long fileLength = file.length();
            return fileLength > 0 ? IoUtils.toByteArray(in, (int)fileLength) : IoUtils.toByteArray(in);
        } finally {
            in.close();
        }
    }

    /**
     * Opens a {@link FileInputStream} for the specified file, providing better error messages than simply calling
     * <code>new FileInputStream(file)</code>.
     * <p>
     * At the end of the method either the stream will be successfully opened, or an exception will have been thrown.
     * <p>
     * An exception is thrown if the file does not exist. An exception is thrown if the file object exists but is a
     * directory. An exception is thrown if the file exists but cannot be read.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

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
