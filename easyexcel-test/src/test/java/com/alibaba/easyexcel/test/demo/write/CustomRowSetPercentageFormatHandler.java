package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 为指定行设置百分比格式
 * @author raxcl
 */
public class CustomRowSetPercentageFormatHandler implements CellWriteHandler {
    private final List<Integer> rowList;

    public CustomRowSetPercentageFormatHandler(Integer... rows) {
        this.rowList = new ArrayList<>();
        Collections.addAll(this.rowList, rows);
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        // 获取当前单元格
        Cell cell = context.getCell();
        int rowIndex = cell.getRowIndex();

        // 如果是我们要设置的行
        if (rowList.contains(rowIndex)) {
            WriteCellData<?> cellData = context.getFirstCellData();
            WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();
            DataFormatData dataFormatData = new DataFormatData();
            dataFormatData.setIndex((short)10);
            // 设置百分比
            writeCellStyle.setDataFormatData(dataFormatData);
        }
    }
}
