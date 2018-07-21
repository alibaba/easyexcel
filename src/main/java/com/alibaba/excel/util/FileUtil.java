package com.alibaba.excel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

/**
 * @author jipengfei
 */
public class FileUtil {

    private static final int BUF = 4096;

    public static boolean writeFile(File file, InputStream stream) throws FileNotFoundException {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
            }

            o = new FileOutputStream(file);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (folderName == null || "".equals(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static String getFolderName(String filePath) {

        if (filePath == null || "".equals(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 文件解压
     * @param path
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean doUnZip(String path, File file) throws IOException {
        ZipFile zipFile = new ZipFile(file, "utf-8");
        Enumeration<ZipArchiveEntry> en = zipFile.getEntries();
        ZipArchiveEntry ze;
        while (en.hasMoreElements()) {
            ze = en.nextElement();
            if(ze.getName().contains("../")){
                //防止目录穿越
                throw new IllegalStateException("unsecurity zipfile!");
            }
            File f = new File(path, ze.getName());
            if (ze.isDirectory()) {
                f.mkdirs();
                continue;
            } else { f.getParentFile().mkdirs(); }

            InputStream is = zipFile.getInputStream(ze);
            OutputStream os = new FileOutputStream(f);
            IOUtils.copy(is, os, BUF);
            is.close();
            os.close();
        }
        zipFile.close();
        return true;
    }

    public static void deletefile(String delpath) {
        File file = new File(delpath);
        // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File delfile = new File(delpath + File.separator + filelist[i]);
                if (!delfile.isDirectory()) {
                    delfile.delete();
                } else if (delfile.isDirectory()) {
                    deletefile(delpath + File.separator + filelist[i]);
                }
            }
            file.delete();
        }
    }


}
