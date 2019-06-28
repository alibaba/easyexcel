package com.alibaba.excel.analysis.v07;

import org.xml.sax.Attributes;

public interface XlsxCellHandler {
    boolean support(String name);
    
    void startHandle(String name, Attributes attributes);
    
    void endHandle(String name);
}
