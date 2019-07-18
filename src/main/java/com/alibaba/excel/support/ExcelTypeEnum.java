package com.alibaba.excel.support;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.FileMagic;

/**
 * @author jipengfei
 */
public enum ExcelTypeEnum {

    XLS(".xls"), XLSX(".xlsx");

    private String value;

    ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public static ExcelTypeEnum valueOf(InputStream inputStream) {
        try {
            if (!inputStream.markSupported()) {
                return null;
            }
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (FileMagic.OLE2.equals(fileMagic)) {
                return XLS;
            }
            if (FileMagic.OOXML.equals(fileMagic)) {
                return XLSX;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
