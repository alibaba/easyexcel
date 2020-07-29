package com.alibaba.excel.analysis.v07.handlers;

import java.math.BigDecimal;

import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.BooleanUtils;

/**
 * Cell Value Handler
 *
 * @author jipengfei
 */
public abstract class AbstractCellValueTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        CellData tempCellData = xlsxReadSheetHolder.getTempCellData();
        StringBuilder tempData = xlsxReadSheetHolder.getTempData();
        CellDataTypeEnum oldType = tempCellData.getType();
        switch (oldType) {
            case DIRECT_STRING:
            case STRING:
            case ERROR:
                tempCellData.setStringValue(tempData.toString());
                break;
            case BOOLEAN:
                tempCellData.setBooleanValue(BooleanUtils.valueOf(tempData.toString()));
                break;
            case NUMBER:
            case EMPTY:
                tempCellData.setType(CellDataTypeEnum.NUMBER);
                tempCellData.setNumberValue(new BigDecimal(tempData.toString()));
                break;
            default:
                throw new IllegalStateException("Cannot set values now");
        }

        // set string value
        setStringValue(xlsxReadContext);

        if (tempCellData.getStringValue() != null
            && xlsxReadContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
            tempCellData.setStringValue(tempCellData.getStringValue());
        }

        tempCellData.checkEmpty();
        xlsxReadSheetHolder.getCellMap().put(xlsxReadSheetHolder.getColumnIndex(), tempCellData);
    }

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {
        xlsxReadContext.xlsxReadSheetHolder().getTempData().append(ch, start, length);
    }

    /**
     * Set string value.
     *
     * @param xlsxReadContext
     */
    protected abstract void setStringValue(XlsxReadContext xlsxReadContext);

}
