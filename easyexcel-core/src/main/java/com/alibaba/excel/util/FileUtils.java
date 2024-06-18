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
import com.alibaba.excel.exception.ExcelCommonException;

import org.apache.poi.util.TempFile;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Apache Software Foundation (ASF)
 */
public class FileUtils {
    public static final String POI_FILES = "poifiles";
    public static final String EX_CACHE = "excache";
    /**
     * If a server has multiple projects in use at the same time, a directory with the same name will be created under
     * the temporary directory, but each project is run by a different user, so there is a permission problem, so each
     * project creates a unique UUID as a separate Temporary Files.
     */
    private static String tempFilePrefix =
        System.getProperty(TempFile.JAVA_IO_TMPDIR) + File.separator + UUID.randomUUID().toString() + File.separator;
    /**
     * Used to store poi temporary files.
     */
    private static String poiFilesPath = tempFilePrefix + POI_FILES + File.separator;
    /**
     * Used to store easy excel temporary files.
     */
    private static String cachePath = tempFilePrefix + EX_CACHE + File.separator;

    private static final int WRITE_BUFF_SIZE = 8192;

    private FileUtils() {}

    static {
        // Create a temporary directory in advance
        File tempFile = new File(tempFilePrefix);
        createDirectory(tempFile);
        tempFile.deleteOnExit();
        // Initialize the cache directory
        File cacheFile = new File(cachePath);
        createDirectory(cacheFile);
    }

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
     * @param file        file
     * @param inputStream inputStream
     */
    public static void writeToFile(File file, InputStream inputStream) {
        writeToFile(file, inputStream, true);
    }

    /**
     * Write inputStream to file
     *
     * @param file             file
     * @param inputStream      inputStream
     * @param closeInputStream closeInputStream
     */
    public static void writeToFile(File file, InputStream inputStream, boolean closeInputStream) {
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
            if (inputStream != null && closeInputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ExcelAnalysisException("Can not close 'inputStream'", e);
                }
            }
        }
    }

    public static void createPoiFilesDirectory() {
        TempFile.setTempFileCreationStrategy(new EasyExcelTempFileCreationStrategy());
    }

    public static File createCacheTmpFile() {
        return createDirectory(new File(cachePath + UUID.randomUUID().toString()));
    }

    public static File createTmpFile(String fileName) {
        File directory = createDirectory(new File(tempFilePrefix));
        return new File(directory, fileName);
    }

    /**
     * @param directory
     */
    public static File createDirectory(File directory) {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new ExcelCommonException("Cannot create directory:" + directory.getAbsolutePath());
        }
        return directory;
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

    public static String getTempFilePrefix() {
        return tempFilePrefix;
    }

    public static void setTempFilePrefix(String tempFilePrefix) {
        FileUtils.tempFilePrefix = tempFilePrefix;
    }

    public static String getPoiFilesPath() {
        return poiFilesPath;
    }

    public static void setPoiFilesPath(String poiFilesPath) {
        FileUtils.poiFilesPath = poiFilesPath;
    }

    public static String getCachePath() {
        return cachePath;
    }

    public static void setCachePath(String cachePath) {
        FileUtils.cachePath = cachePath;
    }
}
