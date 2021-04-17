package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.StringUtils;
import org.xml.sax.Attributes;

import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * Abstract tag handler
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractXlsxTagHandler implements XlsxTagHandler {
    @Override
    public boolean support(XlsxReadContext xlsxReadContext) {
        return true;
    }

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {

    }

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {
        if (!StringUtils.isEmpty(name) && name.equals(ExcelXmlConstants.CELL_TAG)) {
            XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
            Integer colIndex = xlsxReadSheetHolder.getColumnIndex();
            if (colIndex != null){
                CellData cellData = (CellData) xlsxReadSheetHolder.getCellMap().get(colIndex);
                if (cellData != null){
                    cellData.checkEmpty();
                    xlsxReadSheetHolder.getCellMap().put(colIndex, cellData);
                }
            }
        }
    }

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {

    }
}
