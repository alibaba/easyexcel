package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.context.xlsx.XlsxReadContext;

/**
 * Cell Value Handler
 *
 * @author jipengfei
 */
public abstract class AbstractCellValueTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {
        xlsxReadContext.xlsxReadSheetHolder().getTempData().append(ch, start, length);
    }

}
