package com.alibaba.excel.write.handler.context;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.handler.impl.FillStyleCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * cell context
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
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
     * index
     */
    private Integer rowIndex;
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

    /**
     * The value of the original
     */
    private Object originalValue;

    /**
     * The original field type
     */
    private Class<?> originalFieldClass;

    /**
     * Target cell data type
     */
    private CellDataTypeEnum targetCellDataType;

    /**
     * Ignore the filling pattern and the {@code FillStyleCellWriteHandler} will not work.
     *
     * @see FillStyleCellWriteHandler
     */
    private Boolean ignoreFillStyle;

    public CellWriteHandlerContext(WriteContext writeContext,
        WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder,
        WriteTableHolder writeTableHolder, Row row, Integer rowIndex, Cell cell, Integer columnIndex,
        Integer relativeRowIndex, Head headData, List<WriteCellData<?>> cellDataList, WriteCellData<?> firstCellData,
        Boolean head, ExcelContentProperty excelContentProperty) {
        this.writeContext = writeContext;
        this.writeWorkbookHolder = writeWorkbookHolder;
        this.writeSheetHolder = writeSheetHolder;
        this.writeTableHolder = writeTableHolder;
        this.row = row;
        this.rowIndex = rowIndex;
        this.cell = cell;
        this.columnIndex = columnIndex;
        this.relativeRowIndex = relativeRowIndex;
        this.headData = headData;
        this.cellDataList = cellDataList;
        this.firstCellData = firstCellData;
        this.head = head;
        this.excelContentProperty = excelContentProperty;
    }
}
