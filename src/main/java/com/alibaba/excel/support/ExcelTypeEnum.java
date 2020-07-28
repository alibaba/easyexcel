package com.alibaba.excel.support;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.FileMagic;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.util.StringUtils;

/**
 * @author jipengfei
 */
public enum ExcelTypeEnum {
    /**
     * xls
     */
    XLS(".xls"),
    /**
     * xlsx
     */
    XLSX(".xlsx");

    private String value;

    ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public static ExcelTypeEnum valueOf(ReadWorkbook readWorkbook) {
        ExcelTypeEnum excelType = readWorkbook.getExcelType();
        if (excelType != null) {
            return excelType;
        }
        File file = readWorkbook.getFile();
        InputStream inputStream = readWorkbook.getInputStream();
        if (file == null && inputStream == null) {
            throw new ExcelAnalysisException("File and inputStream must be a non-null.");
        }
        try {
            if (file != null) {
                if (!file.exists()) {
                    throw new ExcelAnalysisException("File " + file.getAbsolutePath() + " not exists.");
                }
                // If there is a password, use the FileMagic first
                if (!StringUtils.isEmpty(readWorkbook.getPassword())) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    try {
                        return recognitionExcelType(bufferedInputStream);
                    } finally {
                        bufferedInputStream.close();
                    }
                }
                // Use the name to determine the type
                String fileName = file.getName();
                if (fileName.endsWith(XLSX.getValue())) {
                    return XLSX;
                } else if (fileName.endsWith(XLS.getValue())) {
                    return XLS;
                }
                if (StringUtils.isEmpty(readWorkbook.getPassword())) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    try {
                        return recognitionExcelType(bufferedInputStream);
                    } finally {
                        bufferedInputStream.close();
                    }
                }
            }
            if (!inputStream.markSupported()) {
                inputStream = new BufferedInputStream(inputStream);
                readWorkbook.setInputStream(inputStream);
            }
            return recognitionExcelType(inputStream);
        } catch (ExcelCommonException e) {
            throw e;
        } catch (ExcelAnalysisException e) {
            throw e;
        } catch (Exception e) {
            throw new ExcelCommonException(
                "Convert excel format exception.You can try specifying the 'excelType' yourself", e);
        }
    }

    private static ExcelTypeEnum recognitionExcelType(InputStream inputStream) throws Exception {
        FileMagic fileMagic = FileMagic.valueOf(inputStream);
        if (FileMagic.OLE2.equals(fileMagic)) {
            return XLS;
        }
        if (FileMagic.OOXML.equals(fileMagic)) {
            return XLSX;
        }
        throw new ExcelCommonException(
            "Convert excel format exception.You can try specifying the 'excelType' yourself");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
