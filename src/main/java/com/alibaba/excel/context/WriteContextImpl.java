package com.alibaba.excel.context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;
import com.alibaba.excel.metadata.holder.WriteConfiguration;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;

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
    private WorkbookHolder currentWorkbookHolder;
    /**
     * Current sheet holder
     */
    private SheetHolder currentSheetHolder;
    /**
     * The table currently written
     */
    private TableHolder currentTableHolder;
    /**
     * Configuration of currently operated cell
     */
    private WriteConfiguration currentWriteConfiguration;

    public WriteContextImpl(com.alibaba.excel.metadata.Workbook workbook) {
        if (workbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin to Initialization 'WriteContextImpl'");
        }
        initCurrentWorkbookHolder(workbook);
        beforeWorkbookCreate();
        try {
            currentWorkbookHolder.setWorkbook(WorkBookUtil.createWorkBook(currentWorkbookHolder));
        } catch (Exception e) {
            throw new ExcelGenerateException("Create workbook failure", e);
        }
        afterWorkbookCreate();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'WriteContextImpl' complete");
        }
    }

    private void beforeWorkbookCreate() {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(WorkbookWriteHandler.class);
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
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(WorkbookWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookCreate(currentWorkbookHolder);
            }
        }
    }

    private void initCurrentWorkbookHolder(com.alibaba.excel.metadata.Workbook workbook) {
        currentWorkbookHolder = new WorkbookHolder(workbook);
        currentWriteConfiguration = currentWorkbookHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is currentWorkbookHolder");
        }
    }

    /**
     * @param sheet
     */
    @Override
    public void currentSheet(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        if (sheet.getSheetNo() == null || sheet.getSheetNo() <= 0) {
            sheet.setSheetNo(0);
        }
        if (currentWorkbookHolder.getHasBeenInitializedSheet().containsKey(sheet.getSheetNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sheet:{} is already existed", sheet.getSheetNo());
            }
            currentSheetHolder = currentWorkbookHolder.getHasBeenInitializedSheet().get(sheet.getSheetNo());
            currentSheetHolder.setNewInitialization(Boolean.FALSE);
            currentTableHolder = null;
            currentWriteConfiguration = currentSheetHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is currentSheetHolder");
            }
            return;
        }
        initCurrentSheetHolder(sheet);
        beforeSheetCreate();
        // Initialization current sheet
        initSheet(sheet);
        afterSheetCreate();
    }

    private void beforeSheetCreate() {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(SheetWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).beforeSheetCreate(currentWorkbookHolder, currentSheetHolder);
            }
        }
    }

    private void afterSheetCreate() {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(SheetWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).afterSheetCreate(currentWorkbookHolder, currentSheetHolder);
            }
        }
        if (null != currentWorkbookHolder.getWriteHandler()) {
            currentWorkbookHolder.getWriteHandler().sheet(currentSheetHolder.getSheetNo(),
                currentSheetHolder.getSheet());
        }
    }

    private void initCurrentSheetHolder(com.alibaba.excel.metadata.Sheet sheet) {
        currentSheetHolder = new SheetHolder(sheet, currentWorkbookHolder);
        currentWorkbookHolder.getHasBeenInitializedSheet().put(sheet.getSheetNo(), currentSheetHolder);
        currentTableHolder = null;
        currentWriteConfiguration = currentSheetHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is currentSheetHolder");
        }
    }

    private void initSheet(com.alibaba.excel.metadata.Sheet sheet) {
        Sheet currentSheet;
        try {
            currentSheet = currentWorkbookHolder.getWorkbook().getSheetAt(sheet.getSheetNo());
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Can not find sheet:{} ,now create it", sheet.getSheetNo());
            }
            currentSheet = WorkBookUtil.createSheet(currentWorkbookHolder.getWorkbook(), sheet);
        }
        currentSheetHolder.setSheet(currentSheet);
        // Initialization head
        initHead(currentSheetHolder.getExcelHeadProperty());
    }

    public void initHead(ExcelHeadProperty excelHeadProperty) {
        if (!currentWriteConfiguration.needHead() || !currentSheetHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int lastRowNum = currentSheetHolder.getSheet().getLastRowNum();
        int rowIndex = lastRowNum + currentWriteConfiguration.writeRelativeHeadRowIndex();
        // Combined head
        addMergedRegionToCurrentSheet(excelHeadProperty, rowIndex);
        for (int relativeRowIndex = 0, i = rowIndex; i < excelHeadProperty.getHeadRowNumber() + rowIndex;
            i++, relativeRowIndex++) {
            beforeRowCreate(rowIndex, relativeRowIndex);
            Row row = WorkBookUtil.createRow(currentSheetHolder.getSheet(), i);
            afterRowCreate(row, relativeRowIndex);
            addOneRowOfHeadDataToExcel(row, excelHeadProperty.getHeadMap(), relativeRowIndex);
        }
    }

    private void beforeRowCreate(int rowIndex, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(currentSheetHolder, currentTableHolder, rowIndex,
                    relativeRowIndex, true);
            }
        }
    }

    private void afterRowCreate(Row row, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowCreate(currentSheetHolder, currentTableHolder, row,
                    relativeRowIndex, true);
            }
        }
        if (null != currentWorkbookHolder.getWriteHandler()) {
            currentWorkbookHolder.getWriteHandler().row(row.getRowNum(), row);
        }
    }

    private void addMergedRegionToCurrentSheet(ExcelHeadProperty excelHeadProperty, int rowIndex) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : excelHeadProperty.headCellRangeList()) {
            currentSheetHolder.getSheet().addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + rowIndex,
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
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).beforeCellCreate(currentSheetHolder, currentTableHolder, row, head,
                    relativeRowIndex, true);
            }
        }
    }

    private void afterCellCreate(Head head, Cell cell, int relativeRowIndex) {
        List<WriteHandler> handlerList = currentWriteConfiguration.writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellCreate(currentSheetHolder, currentTableHolder, cell, head,
                    relativeRowIndex, true);
            }
        }
        if (null != currentWorkbookHolder.getWriteHandler()) {
            currentWorkbookHolder.getWriteHandler().cell(cell.getRowIndex(), cell);
        }
    }

    @Override
    public void currentTable(Table table) {
        if (table == null) {
            return;
        }
        if (table.getTableNo() == null || table.getTableNo() <= 0) {
            table.setTableNo(0);
        }
        if (currentSheetHolder.getHasBeenInitializedTable().containsKey(table.getTableNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Table:{} is already existed", table.getTableNo());
            }
            currentTableHolder = currentSheetHolder.getHasBeenInitializedTable().get(table.getTableNo());
            currentTableHolder.setNewInitialization(Boolean.FALSE);
            currentWriteConfiguration = currentTableHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is currentTableHolder");
            }
            return;
        }
        initCurrentTableHolder(table);
        initHead(currentTableHolder.getExcelHeadProperty());
    }

    private void initCurrentTableHolder(com.alibaba.excel.metadata.Table table) {
        currentTableHolder = new TableHolder(table, currentSheetHolder, currentWorkbookHolder);
        currentSheetHolder.getHasBeenInitializedTable().put(table.getTableNo(), currentTableHolder);
        currentWriteConfiguration = currentTableHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is currentTableHolder");
        }
    }

    @Override
    public WriteConfiguration currentConfiguration() {
        return currentWriteConfiguration;
    }

    @Override
    public WorkbookHolder currentWorkbookHolder() {
        return currentWorkbookHolder;
    }

    @Override
    public SheetHolder currentSheetHolder() {
        return currentSheetHolder;
    }

    @Override
    public TableHolder currentTableHolder() {
        return currentTableHolder;
    }

    @Override
    public void finish() {
        try {
            currentWorkbookHolder.getWorkbook().write(currentWorkbookHolder.getOutputStream());
            currentWorkbookHolder.getWorkbook().close();
            if (currentWorkbookHolder.getAutoCloseStream()) {
                if (currentWorkbookHolder.getOutputStream() != null) {
                    currentWorkbookHolder.getOutputStream().close();
                }
                if (currentWorkbookHolder.getTemplateInputStream() != null) {
                    currentWorkbookHolder.getTemplateInputStream().close();
                }
            }
        } catch (IOException e) {
            throw new ExcelGenerateException("Can not close IO", e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Finished write.");
        }
    }
}
