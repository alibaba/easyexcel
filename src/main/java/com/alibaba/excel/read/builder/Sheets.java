package com.alibaba.excel.read.builder;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import java.io.IOException;

public abstract class Sheets {
    public static void  createWorkBook() throws IOException {

    }

    abstract void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException;
}
