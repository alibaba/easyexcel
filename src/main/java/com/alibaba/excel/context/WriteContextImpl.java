package com.alibaba.excel.context;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;

/**
 * A context is the main anchorage point of a excel writer.
 *
 * @author jipengfei
 */
public class WriteContextImpl implements WriteContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteContextImpl.class);

    /**
     * The Workbook currently written
     */
    private WriteWorkbookHolder writeWorkbookHolder;
    /**
     * Current sheet holder
     */
    private WriteSheetHolder writeSheetHolder;
    /**
     * The table currently written
     */
    private WriteTableHolder writeTableHolder;
    /**
     * Configuration of currently operated cell
     */
    private WriteHolder currentWriteHolder;

    public WriteContextImpl(WriteWorkbook writeWorkbook) {
        if (writeWorkbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin to Initialization 'WriteContextImpl'");
        }
        initCurrentWorkbookHolder(writeWorkbook);
        beforeWorkbookCreate();
        try {
            writeWorkbookHolder.setWorkbook(WorkBookUtil.createWorkBook(writeWorkbookHolder));
        } catch (Exception e) {
            throw new ExcelGenerateException("Create workbook failure", e);
        }
        afterWorkbookCreate();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'WriteContextImpl' complete");
        }
    }

    private void beforeWorkbookCreate() {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(WorkbookWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).beforeWorkbookCreate();
            }
        }
    }

    private void afterWorkbookCreate() {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(WorkbookWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookCreate(writeWorkbookHolder);
            }
        }
    }

    private void initCurrentWorkbookHolder(WriteWorkbook writeWorkbook) {
        writeWorkbookHolder = new WriteWorkbookHolder(writeWorkbook);
        currentWriteHolder = writeWorkbookHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeWorkbookHolder");
        }
    }

    /**
     * @param writeSheet
     */
    @Override
    public void currentSheet(WriteSheet writeSheet) {
        if (writeSheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        if (writeSheet.getSheetNo() == null || writeSheet.getSheetNo() <= 0) {
            LOGGER.info("Sheet number is null");
            writeSheet.setSheetNo(0);
        }
        if (writeWorkbookHolder.getHasBeenInitializedSheet().containsKey(writeSheet.getSheetNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sheet:{} is already existed", writeSheet.getSheetNo());
            }
            writeSheetHolder = writeWorkbookHolder.getHasBeenInitializedSheet().get(writeSheet.getSheetNo());
            writeSheetHolder.setNewInitialization(Boolean.FALSE);
            writeTableHolder = null;
            currentWriteHolder = writeSheetHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is writeSheetHolder");
            }
            return;
        }
        initCurrentSheetHolder(writeSheet);
        beforeSheetCreate();
        // Initialization current sheet
        initSheet();
        afterSheetCreate();
    }

    private void beforeSheetCreate() {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(SheetWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).beforeSheetCreate(writeWorkbookHolder, writeSheetHolder);
            }
        }
    }

    private void afterSheetCreate() {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(SheetWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).afterSheetCreate(writeWorkbookHolder, writeSheetHolder);
            }
        }
        if (null != writeWorkbookHolder.getWriteWorkbook().getWriteHandler()) {
            writeWorkbookHolder.getWriteWorkbook().getWriteHandler().sheet(writeSheetHolder.getSheetNo(),
                writeSheetHolder.getSheet());
        }
    }

    private void initCurrentSheetHolder(WriteSheet writeSheet) {
        writeSheetHolder = new WriteSheetHolder(writeSheet, writeWorkbookHolder);
        writeWorkbookHolder.getHasBeenInitializedSheet().put(writeSheet.getSheetNo(), writeSheetHolder);
        writeTableHolder = null;
        currentWriteHolder = writeSheetHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeSheetHolder");
        }
    }

    private void initSheet() {
        Sheet currentSheet;
        try {
            currentSheet = writeWorkbookHolder.getWorkbook().getSheetAt(writeSheetHolder.getSheetNo());
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Can not find sheet:{} ,now create it", writeSheetHolder.getSheetNo());
            }
            currentSheet = WorkBookUtil.createSheet(writeWorkbookHolder.getWorkbook(), writeSheetHolder.getSheetName());
        }
        writeSheetHolder.setSheet(currentSheet);
        // Initialization head
        initHead(writeSheetHolder.excelWriteHeadProperty());
    }

    public void initHead(ExcelWriteHeadProperty excelWriteHeadProperty) {
        if (!currentWriteHolder.needHead() || !currentWriteHolder.excelWriteHeadProperty().hasHead()) {
            return;
        }
        int newRowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite();
        newRowIndex += currentWriteHolder.relativeHeadRowIndex();
        // Combined head
        addMergedRegionToCurrentSheet(excelWriteHeadProperty, newRowIndex);
        for (int relativeRowIndex = 0, i = newRowIndex; i < excelWriteHeadProperty.getHeadRowNumber() + newRowIndex;
            i++, relativeRowIndex++) {
            beforeRowCreate(newRowIndex, relativeRowIndex);
            Row row = WorkBookUtil.createRow(writeSheetHolder.getSheet(), i);
            afterRowCreate(row, relativeRowIndex);
            addOneRowOfHeadDataToExcel(row, excelWriteHeadProperty.getHeadMap(), relativeRowIndex);
        }
    }

    private void beforeRowCreate(int rowIndex, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(writeSheetHolder, writeTableHolder, rowIndex,
                    relativeRowIndex, true);
            }
        }
    }

    private void afterRowCreate(Row row, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowCreate(writeSheetHolder, writeTableHolder, row,
                    relativeRowIndex, true);
            }
        }
        if (null != writeWorkbookHolder.getWriteWorkbook().getWriteHandler()) {
            writeWorkbookHolder.getWriteWorkbook().getWriteHandler().row(row.getRowNum(), row);
        }
    }

    private void addMergedRegionToCurrentSheet(ExcelWriteHeadProperty excelWriteHeadProperty, int rowIndex) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : excelWriteHeadProperty.headCellRangeList()) {
            writeSheetHolder.getSheet().addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + rowIndex,
                cellRangeModel.getLastRow() + rowIndex, cellRangeModel.getFirstCol(), cellRangeModel.getLastCol()));
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, Map<Integer, Head> headMap, int relativeRowIndex) {
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Head head = entry.getValue();
            beforeCellCreate(row, head, relativeRowIndex);
            Cell cell = WorkBookUtil.createCell(row, entry.getKey(), head.getHeadNameList().get(relativeRowIndex));
            afterCellCreate(head, cell, relativeRowIndex);
        }
    }

    private void beforeCellCreate(Row row, Head head, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).beforeCellCreate(writeSheetHolder, writeTableHolder, row, head,
                    relativeRowIndex, true);
            }
        }
    }

    private void afterCellCreate(Head head, Cell cell, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteHolder.writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellCreate(writeSheetHolder, writeTableHolder, null, cell, head,
                    relativeRowIndex, true);
            }
        }
        if (null != writeWorkbookHolder.getWriteWorkbook().getWriteHandler()) {
            writeWorkbookHolder.getWriteWorkbook().getWriteHandler().cell(cell.getRowIndex(), cell);
        }
    }

    @Override
    public void currentTable(WriteTable writeTable) {
        if (writeTable == null) {
            return;
        }
        if (writeTable.getTableNo() == null || writeTable.getTableNo() <= 0) {
            writeTable.setTableNo(0);
        }
        if (writeSheetHolder.getHasBeenInitializedTable().containsKey(writeTable.getTableNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Table:{} is already existed", writeTable.getTableNo());
            }
            writeTableHolder = writeSheetHolder.getHasBeenInitializedTable().get(writeTable.getTableNo());
            writeTableHolder.setNewInitialization(Boolean.FALSE);
            currentWriteHolder = writeTableHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is writeTableHolder");
            }
            return;
        }
        initCurrentTableHolder(writeTable);
        initHead(writeTableHolder.excelWriteHeadProperty());
    }

    private void initCurrentTableHolder(WriteTable writeTable) {
        writeTableHolder = new WriteTableHolder(writeTable, writeSheetHolder, writeWorkbookHolder);
        writeSheetHolder.getHasBeenInitializedTable().put(writeTable.getTableNo(), writeTableHolder);
        currentWriteHolder = writeTableHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeTableHolder");
        }
    }

    @Override
    public WriteWorkbookHolder writeWorkbookHolder() {
        return writeWorkbookHolder;
    }

    @Override
    public WriteSheetHolder writeSheetHolder() {
        return writeSheetHolder;
    }

    @Override
    public WriteTableHolder writeTableHolder() {
        return writeTableHolder;
    }

    @Override
    public WriteHolder currentWriteHolder() {
        return currentWriteHolder;
    }

    @Override
    public void finish() {
        if (writeWorkbookHolder == null) {
            return;
        }
        try {
            writeWorkbookHolder.getWorkbook().write(writeWorkbookHolder.getOutputStream());
            writeWorkbookHolder.getWorkbook().close();
            if (writeWorkbookHolder.getAutoCloseStream()) {
                if (writeWorkbookHolder.getOutputStream() != null) {
                    writeWorkbookHolder.getOutputStream().close();
                }
                if (writeWorkbookHolder.getTemplateInputStream() != null) {
                    writeWorkbookHolder.getTemplateInputStream().close();
                }
            } else {
                if (writeWorkbookHolder.getFile() != null && writeWorkbookHolder.getOutputStream() != null) {
                    writeWorkbookHolder.getOutputStream().close();
                }
            }
        } catch (IOException e) {
            throw new ExcelGenerateException("Can not close IO", e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Finished write.");
        }
    }

    @Override
    public Sheet getCurrentSheet() {
        return writeSheetHolder.getSheet();
    }

    @Override
    public boolean needHead() {
        return writeSheetHolder.needHead();
    }

    @Override
    public OutputStream getOutputStream() {
        return writeWorkbookHolder.getOutputStream();
    }

    @Override
    public Workbook getWorkbook() {
        return writeWorkbookHolder.getWorkbook();
    }
}
