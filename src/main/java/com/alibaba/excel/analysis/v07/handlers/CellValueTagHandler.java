package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Cell Value Handler
 *
 * @author jipengfei
 */
public class CellValueTagHandler extends AbstractCellValueTagHandler {

    @Override
    protected void setStringValue(XlsxReadContext xlsxReadContext) {
        // Have to go "sharedStrings.xml" and get it
        CellData tempCellData = xlsxReadContext.xlsxReadSheetHolder().getTempCellData();
        switch (tempCellData.getType()) {
            case STRING:
                String stringValue = xlsxReadContext.readWorkbookHolder().getReadCache()
                    .get(Integer.valueOf(tempCellData.getStringValue()));
                if (stringValue != null && xlsxReadContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
                    stringValue = stringValue.trim();
                }
                tempCellData.setStringValue(stringValue);
                break;
            case DIRECT_STRING:
                tempCellData.setType(CellDataTypeEnum.STRING);
                break;
            default:
        }
    }

}
