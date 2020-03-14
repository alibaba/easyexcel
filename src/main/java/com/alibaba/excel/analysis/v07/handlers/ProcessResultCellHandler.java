package com.alibaba.excel.analysis.v07.handlers;

import org.xml.sax.Attributes;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.PositionUtils;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class ProcessResultCellHandler implements XlsxCellHandler {

    @Override
    public void startHandle(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        xlsxReadContext.readRowHolder(
            new ReadRowHolder(PositionUtils.getRowByRowTagt(attributes.getValue(ExcelXmlConstants.POSITION)), RowTypeEnum.DATA,
                xlsxReadContext.readSheetHolder().getGlobalConfiguration()));
    }

    @Override
    public void endHandle(XlsxReadContext xlsxReadContext, String name) {
        xlsxReadContext.analysisEventProcessor().endRow(xlsxReadContext);
    }

}
