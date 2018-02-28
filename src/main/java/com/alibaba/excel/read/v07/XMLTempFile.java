package com.alibaba.excel.read.v07;

import java.io.File;
import java.security.SecureRandom;

import com.alibaba.excel.util.EasyExcelTempFile;

/**
 * @author jipengfei
 *
 */
public class XMLTempFile {

    private static final String TMP_FILE_NAME = "tmp.xlsx";

    private static final String XL = "xl";

    private static final String XML_WORKBOOK = "workbook.xml";

    private static final String XML_SHARED_STRING = "sharedStrings.xml";

    private static final String SHEET = "sheet";

    private static final String WORK_SHEETS = "worksheets";

    private static final SecureRandom random = new SecureRandom();

    public static String getTmpFilePath(String path) {
        return path + File.separator + TMP_FILE_NAME;
    }

    public static String createPath() {
        return EasyExcelTempFile.getEasyExcelTmpDir() + File.separator + random.nextLong();
    }

    public static String getWorkBookFilePath(String path) {
        return path + File.separator + XL + File.separator + XML_WORKBOOK;
    }

    public static String getSharedStringFilePath(String path) {
        return path + File.separator + XL + File.separator + XML_SHARED_STRING;
    }

    public static String getSheetFilePath(String path, int id) {
        return path + File.separator + XL + File.separator + WORK_SHEETS + File.separator + SHEET + id
            + ".xml";
    }
}
