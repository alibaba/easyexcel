package com.alibaba.easyexcel.test.core.handler;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 *
 *
 * @author JiaJu Zhuang
 **/
public class WriteHandler implements WorkbookWriteHandler, SheetWriteHandler, RowWriteHandler, CellWriteHandler {

    private long beforeCellCreate = 0L;
    private long afterCellCreate = 0L;
    private long afterCellDataConverted = 0L;
    private long afterCellDispose = 0L;
    private long beforeRowCreate = 0L;
    private long afterRowCreate = 0L;
    private long afterRowDispose = 0L;
    private long beforeSheetCreate = 0L;
    private long afterSheetCreate = 0L;
    private long beforeWorkbookCreate = 0L;
    private long afterWorkbookCreate = 0L;
    private long afterWorkbookDispose = 0L;


    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(0L, beforeCellCreate);
            Assert.assertEquals(0L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(0L, afterCellDispose);
            Assert.assertEquals(1L, beforeRowCreate);
            Assert.assertEquals(1L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            beforeCellCreate++;
        }

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(1L, beforeCellCreate);
            Assert.assertEquals(0L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(0L, afterCellDispose);
            Assert.assertEquals(1L, beforeRowCreate);
            Assert.assertEquals(1L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            afterCellCreate++;
        }
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Assert.assertEquals(1L, beforeCellCreate);
        Assert.assertEquals(1L, afterCellCreate);
        Assert.assertEquals(0L, afterCellDataConverted);
        Assert.assertEquals(1, afterCellDispose);
        Assert.assertEquals(1L, beforeRowCreate);
        Assert.assertEquals(1L, afterRowCreate);
        Assert.assertEquals(1L, afterRowDispose);
        Assert.assertEquals(1L, beforeSheetCreate);
        Assert.assertEquals(1L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        afterCellDataConverted++;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(1L, beforeCellCreate);
            Assert.assertEquals(1L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(0L, afterCellDispose);
            Assert.assertEquals(1L, beforeRowCreate);
            Assert.assertEquals(1L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            afterCellDispose++;
        }
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
        Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(0L, beforeCellCreate);
            Assert.assertEquals(0L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(0L, afterCellDispose);
            Assert.assertEquals(0L, beforeRowCreate);
            Assert.assertEquals(0L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            beforeRowCreate++;
        }

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(0L, beforeCellCreate);
            Assert.assertEquals(0L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(0L, afterCellDispose);
            Assert.assertEquals(1L, beforeRowCreate);
            Assert.assertEquals(0L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            afterRowCreate++;
        }
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Assert.assertEquals(1L, beforeCellCreate);
            Assert.assertEquals(1L, afterCellCreate);
            Assert.assertEquals(0L, afterCellDataConverted);
            Assert.assertEquals(1L, afterCellDispose);
            Assert.assertEquals(1L, beforeRowCreate);
            Assert.assertEquals(1L, afterRowCreate);
            Assert.assertEquals(0L, afterRowDispose);
            Assert.assertEquals(1L, beforeSheetCreate);
            Assert.assertEquals(1L, afterSheetCreate);
            Assert.assertEquals(1L, beforeWorkbookCreate);
            Assert.assertEquals(1L, afterWorkbookCreate);
            Assert.assertEquals(0L, afterWorkbookDispose);
            afterRowDispose++;
        }
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Assert.assertEquals(0L, beforeCellCreate);
        Assert.assertEquals(0L, afterCellCreate);
        Assert.assertEquals(0L, afterCellDataConverted);
        Assert.assertEquals(0L, afterCellDispose);
        Assert.assertEquals(0L, beforeRowCreate);
        Assert.assertEquals(0L, afterRowCreate);
        Assert.assertEquals(0L, afterRowDispose);
        Assert.assertEquals(0L, beforeSheetCreate);
        Assert.assertEquals(0L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        beforeSheetCreate++;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Assert.assertEquals(0L, beforeCellCreate);
        Assert.assertEquals(0L, afterCellCreate);
        Assert.assertEquals(0L, afterCellDataConverted);
        Assert.assertEquals(0L, afterCellDispose);
        Assert.assertEquals(0L, beforeRowCreate);
        Assert.assertEquals(0L, afterRowCreate);
        Assert.assertEquals(0L, afterRowDispose);
        Assert.assertEquals(1L, beforeSheetCreate);
        Assert.assertEquals(0L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        afterSheetCreate++;
    }

    @Override
    public void beforeWorkbookCreate() {
        Assert.assertEquals(0L, beforeCellCreate);
        Assert.assertEquals(0L, afterCellCreate);
        Assert.assertEquals(0L, afterCellDataConverted);
        Assert.assertEquals(0L, afterCellDispose);
        Assert.assertEquals(0L, beforeRowCreate);
        Assert.assertEquals(0L, afterRowCreate);
        Assert.assertEquals(0L, afterRowDispose);
        Assert.assertEquals(0L, beforeSheetCreate);
        Assert.assertEquals(0L, afterSheetCreate);
        Assert.assertEquals(0L, beforeWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        beforeWorkbookCreate++;
    }

    @Override
    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {
        Assert.assertEquals(0L, beforeCellCreate);
        Assert.assertEquals(0L, afterCellCreate);
        Assert.assertEquals(0L, afterCellDataConverted);
        Assert.assertEquals(0L, afterCellDispose);
        Assert.assertEquals(0L, beforeRowCreate);
        Assert.assertEquals(0L, afterRowCreate);
        Assert.assertEquals(0L, afterRowDispose);
        Assert.assertEquals(0L, beforeSheetCreate);
        Assert.assertEquals(0L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        afterWorkbookCreate++;
    }

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
        Assert.assertEquals(1L, beforeCellCreate);
        Assert.assertEquals(1L, afterCellCreate);
        Assert.assertEquals(1L, afterCellDataConverted);
        Assert.assertEquals(1L, afterCellDispose);
        Assert.assertEquals(1L, beforeRowCreate);
        Assert.assertEquals(1L, afterRowCreate);
        Assert.assertEquals(1L, afterRowDispose);
        Assert.assertEquals(1L, beforeSheetCreate);
        Assert.assertEquals(1L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookCreate);
        Assert.assertEquals(0L, afterWorkbookDispose);
        afterWorkbookDispose++;

    }

    public void afterAll() {
        Assert.assertEquals(1L, beforeCellCreate);
        Assert.assertEquals(1L, afterCellCreate);
        Assert.assertEquals(1L, afterCellDataConverted);
        Assert.assertEquals(1L, afterCellDispose);
        Assert.assertEquals(1L, beforeRowCreate);
        Assert.assertEquals(1L, afterRowCreate);
        Assert.assertEquals(1L, afterRowDispose);
        Assert.assertEquals(1L, beforeSheetCreate);
        Assert.assertEquals(1L, afterSheetCreate);
        Assert.assertEquals(1L, beforeWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookCreate);
        Assert.assertEquals(1L, afterWorkbookDispose);
    }
}
