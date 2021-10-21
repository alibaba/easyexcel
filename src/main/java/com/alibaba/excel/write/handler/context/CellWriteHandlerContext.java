package com.alibaba.excel.write.handler.context;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * cell context
 *
 * @author Jiaju Zhuang
 */
@Data
@AllArgsConstructor
public class CellWriteHandlerContext {
    /**
     * write context
     */
    private WriteContext writeContext;
    /**
     * workbook
     */
    private WriteWorkbookHolder writeWorkbookHolder;
    /**
     * sheet
     */
    private WriteSheetHolder writeSheetHolder;
    /**
     * table .Nullable.It is null without using table writes.
     */
    private WriteTableHolder writeTableHolder;
    /**
     * row
     */
    private Row row;
    /**
     * cell
     */
    private Cell cell;
    /**
     * index
     */
    private Integer columnIndex;
    /**
     * Nullable.It is null in the case of fill data.
     */
    private Integer relativeRowIndex;
    /**
     * Nullable.It is null in the case of fill data.
     */
    private Head headData;
    /**
     * Nullable.It is null in the case of add header.There may be several when fill the data.
     */
    private List<WriteCellData<?>> cellDataList;
    /**
     * Nullable.
     * It is null in the case of add header.
     * In the case of write there must be not null.
     * firstCellData == cellDataList.get(0)
     */
    private WriteCellData<?> firstCellData;
    /**
     * Nullable.It is null in the case of fill data.
     */
    private Boolean head;
    /**
     * Field annotation configuration information.
     */
    private ExcelContentProperty excelContentProperty;
}
