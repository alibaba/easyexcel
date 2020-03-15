package com.alibaba.excel.analysis.v07.handlers;

import java.util.LinkedHashMap;

import org.xml.sax.Attributes;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.PositionUtils;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class RowTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        int rowIndex = PositionUtils.getRowByRowTagt(attributes.getValue(ExcelXmlConstants.ATTRIBUTE_R));
        Integer lastIndex = xlsxReadContext.readSheetHolder().getRowIndex();
        if (lastIndex != null) {
            while (lastIndex + 1 < rowIndex) {
                xlsxReadContext.readRowHolder(new ReadRowHolder(lastIndex + 1, RowTypeEnum.EMPTY,
                    xlsxReadContext.readSheetHolder().getGlobalConfiguration(), new LinkedHashMap<Integer, Cell>()));
                xlsxReadContext.analysisEventProcessor().endRow(xlsxReadContext);
                xlsxReadContext.xlsxReadSheetHolder().setCellMap(new LinkedHashMap<Integer, Cell>());
                lastIndex++;
            }
        }
        xlsxReadContext.readSheetHolder().setRowIndex(rowIndex);
    }

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {
        xlsxReadContext.readRowHolder(new ReadRowHolder(xlsxReadContext.readSheetHolder().getRowIndex(),
            RowTypeEnum.DATA, xlsxReadContext.readSheetHolder().getGlobalConfiguration(),
            xlsxReadContext.xlsxReadSheetHolder().getCellMap()));
        xlsxReadContext.analysisEventProcessor().endRow(xlsxReadContext);
        xlsxReadContext.xlsxReadSheetHolder().setCellMap(new LinkedHashMap<Integer, Cell>());
    }

}
