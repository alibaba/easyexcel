package com.alibaba.excel.parameter;

import java.io.OutputStream;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * {@link com.alibaba.excel.ExcelWriter}
 *
 * @author jipengfei
 */
@Deprecated
public class ExcelWriteParam {

    /**
     */
    private OutputStream outputStream;

    /**
     */
    private ExcelTypeEnum type;

    public ExcelWriteParam(OutputStream outputStream, ExcelTypeEnum type) {
        this.outputStream = outputStream;
        this.type = type;

    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ExcelTypeEnum getType() {
        return type;
    }

    public void setType(ExcelTypeEnum type) {
        this.type = type;
    }
}
