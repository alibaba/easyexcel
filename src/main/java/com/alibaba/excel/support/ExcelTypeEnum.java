package com.alibaba.excel.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.FileMagic;

import com.alibaba.excel.exception.ExcelCommonException;

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

    public static ExcelTypeEnum valueOf(File file, InputStream inputStream) {
        try {
            FileMagic fileMagic;
            if (file != null) {
                fileMagic = FileMagic.valueOf(file);
                if (!FileMagic.OLE2.equals(fileMagic) && !FileMagic.OOXML.equals(fileMagic)) {
                    String fileName = file.getName();
                    if (fileName.endsWith(XLSX.getValue())) {
                        return XLSX;
                    } else if (fileName.endsWith(XLS.getValue())) {
                        return XLS;
                    } else {
                        throw new ExcelCommonException("Unknown excel type.");
                    }
                }
            } else {
                fileMagic = FileMagic.valueOf(inputStream);
            }
            if (FileMagic.OLE2.equals(fileMagic)) {
                return XLS;
            }
            if (FileMagic.OOXML.equals(fileMagic)) {
                return XLSX;
            }
        } catch (IOException e) {
            throw new ExcelCommonException(
                "Convert excel format exception.You can try specifying the 'excelType' yourself", e);
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
