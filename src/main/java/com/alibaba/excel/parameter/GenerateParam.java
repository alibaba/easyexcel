package com.alibaba.excel.parameter;

import java.io.OutputStream;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Created by jipengfei on 17/2/19.
 *
 * @author jipengfei
 * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
 */
@Deprecated
public class GenerateParam {

    private OutputStream outputStream;

    private String sheetName;

    private Class clazz;

    private ExcelTypeEnum type;

    private boolean needHead = true;

    public GenerateParam(String sheetName, Class clazz, OutputStream outputStream) {
        this.outputStream = outputStream;
        this.sheetName = sheetName;
        this.clazz = clazz;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public ExcelTypeEnum getType() {
        return type;
    }

    public void setType(ExcelTypeEnum type) {
        this.type = type;
    }

    public boolean isNeedHead() {
        return needHead;
    }

    public void setNeedHead(boolean needHead) {
        this.needHead = needHead;
    }
}
