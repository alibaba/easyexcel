package com.alibaba.excel.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterBuilder;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.DefaultWriteHandlerBuilder;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;

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
    private ConfigurationSelector currentConfigurationSelector;

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
            currentWorkbookHolder.setWorkbook(WorkBookUtil.createWorkBook(workbook));
        } catch (IOException e) {
            throw new ExcelGenerateException("Create workbook failure", e);
        }
        afterWorkbookCreate();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'WriteContextImpl' complete");
        }
    }

    private void beforeWorkbookCreate() {
        List<WriteHandler> handlerList = currentConfigurationSelector.writeHandlerMap().get(WorkbookWriteHandler.class);
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
        List<WriteHandler> handlerList = currentConfigurationSelector.writeHandlerMap().get(WorkbookWriteHandler.class);
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
        currentWorkbookHolder = new WorkbookHolder();
        currentWorkbookHolder.setWorkbookParam(workbook);
        currentWorkbookHolder.setTemplateInputStream(workbook.getTemplateInputStream());
        currentWorkbookHolder.setOutputStream(workbook.getOutputStream());
        if (workbook.getNeedHead() == null) {
            currentWorkbookHolder.setNeedHead(Boolean.TRUE);
        } else {
            currentWorkbookHolder.setNeedHead(workbook.getNeedHead());
        }
        if (workbook.getWriteRelativeHeadRowIndex() == null) {
            currentWorkbookHolder.setWriteRelativeHeadRowIndex(0);
        } else {
            currentWorkbookHolder.setWriteRelativeHeadRowIndex(workbook.getWriteRelativeHeadRowIndex());
        }
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (workbook.getCustomWriteHandlerList() != null && !workbook.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(workbook.getCustomWriteHandlerList());
        }
        handlerList.addAll(DefaultWriteHandlerBuilder.loadDefaultHandler());
        currentWorkbookHolder.setWriteHandlerList(sortAndClearUpHandler(handlerList));
        Map<Class, Converter> converterMap = DefaultConverterBuilder.loadDefaultWriteConverter();
        if (workbook.getCustomConverterMap() != null && !workbook.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(workbook.getCustomConverterMap());
        }
        currentWorkbookHolder.setConverterMap(converterMap);
        currentWorkbookHolder.setHasBeenInitializedSheet(new HashMap<Integer, SheetHolder>());
        currentConfigurationSelector = currentWorkbookHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfigurationSelector is currentWorkbookHolder");
        }
    }

    private List<WriteHandler> sortAndClearUpHandler(List<WriteHandler> handlerList) {
        // sort
        Map<Integer, List<WriteHandler>> orderExcelWriteHandlerMap = new TreeMap<Integer, List<WriteHandler>>();
        for (WriteHandler handler : handlerList) {
            int order = Integer.MIN_VALUE;
            if (handler instanceof Order) {
                order = ((Order)handler).order();
            }
            if (orderExcelWriteHandlerMap.containsKey(order)) {
                orderExcelWriteHandlerMap.get(order).add(handler);
            } else {
                List<WriteHandler> tempHandlerList = new ArrayList<WriteHandler>();
                tempHandlerList.add(handler);
                orderExcelWriteHandlerMap.put(order, tempHandlerList);
            }
        }
        // clean up
        Set<String> alreadyExistedHandlerSet = new HashSet<String>();
        List<WriteHandler> result = new ArrayList<WriteHandler>();
        for (Map.Entry<Integer, List<WriteHandler>> entry : orderExcelWriteHandlerMap.entrySet()) {
            for (WriteHandler handler : entry.getValue()) {
                if (handler instanceof NotRepeatExecutor) {
                    String uniqueValue = ((NotRepeatExecutor)handler).uniqueValue();
                    if (alreadyExistedHandlerSet.contains(uniqueValue)) {
                        continue;
                    }
                    alreadyExistedHandlerSet.add(uniqueValue);
                }
                result.add(handler);
            }
        }
        return result;
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
            currentTableHolder = null;
            currentConfigurationSelector = currentSheetHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfigurationSelector is currentSheetHolder");
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
        List<WriteHandler> handlerList = currentConfigurationSelector.writeHandlerMap().get(SheetWriteHandler.class);
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
        List<WriteHandler> handlerList = currentConfigurationSelector.writeHandlerMap().get(SheetWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).afterSheetCreate(currentWorkbookHolder, currentSheetHolder);
            }
        }
    }

    private void initCurrentSheetHolder(com.alibaba.excel.metadata.Sheet sheet) {
        currentSheetHolder = new SheetHolder();
        currentSheetHolder.setSheetParam(sheet);
        currentSheetHolder.setParentWorkBook(currentWorkbookHolder);
        if (sheet.getNeedHead() == null) {
            currentSheetHolder.setNeedHead(currentConfigurationSelector.needHead());
        } else {
            currentSheetHolder.setNeedHead(sheet.getNeedHead());
        }
        if (sheet.getWriteRelativeHeadRowIndex() == null) {
            currentSheetHolder.setWriteRelativeHeadRowIndex(currentConfigurationSelector.writeRelativeHeadRowIndex());
        } else {
            currentSheetHolder.setWriteRelativeHeadRowIndex(sheet.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(sheet);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (sheet.getCustomWriteHandlerList() != null && !sheet.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(sheet.getCustomWriteHandlerList());
        }
        handlerList.addAll(currentConfigurationSelector.writeHandlerList());
        currentSheetHolder.setWriteHandlerList(sortAndClearUpHandler(handlerList));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(currentConfigurationSelector.converterMap());
        if (sheet.getCustomConverterMap() != null && !sheet.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(sheet.getCustomConverterMap());
        }
        currentSheetHolder.setConverterMap(converterMap);
        currentSheetHolder.setHasBeenInitializedTable(new HashMap<Integer, TableHolder>());
        currentWorkbookHolder.getHasBeenInitializedSheet().put(sheet.getSheetNo(), currentSheetHolder);
        currentTableHolder = null;
        currentConfigurationSelector = currentSheetHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfigurationSelector is currentSheetHolder");
        }
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private void compatibleOldCode(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet.getColumnWidthMap() != null && !sheet.getColumnWidthMap().isEmpty()) {
            final Map<Integer, Integer> columnWidthMap = sheet.getColumnWidthMap();
            if (sheet.getCustomWriteHandlerList() == null) {
                sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            sheet.getCustomWriteHandlerList().add(new AbstractHeadColumnWidthStyleStrategy() {
                @Override
                protected int columnWidth(Head head) {
                    if (columnWidthMap.containsKey(head.getColumnIndex())) {
                        columnWidthMap.get(head.getColumnIndex());
                    }
                    return 20;
                }
            });
        }
        if (sheet.getTableStyle() != null) {
            final TableStyle tableStyle = sheet.getTableStyle();
            if (sheet.getCustomWriteHandlerList() == null) {
                sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            CellStyle headCellStyle = new CellStyle();
            headCellStyle.setFont(tableStyle.getTableHeadFont());
            headCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            CellStyle contentCellStyle = new CellStyle();
            contentCellStyle.setFont(tableStyle.getTableContentFont());
            contentCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            sheet.getCustomWriteHandlerList().add(new RowCellStyleStrategy(headCellStyle, contentCellStyle));
        }
    }

    private void initSheet(com.alibaba.excel.metadata.Sheet sheet) {
        Sheet currentSheet;
        try {
            currentSheet = currentWorkbookHolder.getWorkbook().getSheetAt(sheet.getSheetNo());
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("Can not find sheet:{} ,now create it", sheet.getSheetNo());
            }
            currentSheet = WorkBookUtil.createSheet(currentWorkbookHolder.getWorkbook(), sheet);
        }
        currentSheetHolder.setSheet(currentSheet);
        // Initialization head
        currentSheetHolder.setExcelHeadProperty(new ExcelHeadProperty(sheet.getClazz(), sheet.getHead()));
        // Initialization head
        initHead(currentSheetHolder.getExcelHeadProperty());
    }

    public void initHead(ExcelHeadProperty excelHeadProperty) {
        if (!currentConfigurationSelector.needHead() || !currentSheetHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int startRow = currentSheetHolder.getSheet().getLastRowNum();
        startRow += currentConfigurationSelector.writeRelativeHeadRowIndex();
        // Combined head
        addMergedRegionToCurrentSheet(excelHeadProperty, startRow);
        for (int i = startRow; i < excelHeadProperty.getHeadRowNumber() + startRow; i++) {
            Row row = WorkBookUtil.createRow(currentSheetHolder.getSheet(), i);
            addOneRowOfHeadDataToExcel(row, i, excelHeadProperty.getHeadList());
        }
    }

    private void addMergedRegionToCurrentSheet(ExcelHeadProperty excelHeadProperty, int startRow) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : excelHeadProperty.getCellRangeModels()) {
            currentSheetHolder.getSheet().addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + startRow,
                cellRangeModel.getLastRow() + startRow, cellRangeModel.getFirstCol(), cellRangeModel.getLastCol()));
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, int rowIndex, List<Head> headList) {
        for (int i = 0; i < headList.size(); i++) {
            Head head = headList.get(i);
            // TODO 创建cell
            Cell cell = WorkBookUtil.createCell(row, i, head.getHeadName(i));
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
            currentConfigurationSelector = currentTableHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfigurationSelector is currentTableHolder");
            }
            return;
        }
        initCurrentTableHolder(table);
        // Initialization head
        currentTableHolder.setExcelHeadProperty(new ExcelHeadProperty(table.getClazz(), table.getHead()));

        initHead(currentTableHolder.getExcelHeadProperty());
    }

    private void initCurrentTableHolder(com.alibaba.excel.metadata.Table table) {
        currentTableHolder = new TableHolder();
        currentTableHolder.setTableParam(table);
        currentTableHolder.setParentSheet(currentSheetHolder);
        currentTableHolder.setTableNo(table.getTableNo());
        if (table.getNeedHead() == null) {
            currentTableHolder.setNeedHead(currentConfigurationSelector.needHead());
        } else {
            currentTableHolder.setNeedHead(table.getNeedHead());
        }
        if (table.getWriteRelativeHeadRowIndex() == null) {
            currentTableHolder.setWriteRelativeHeadRowIndex(currentConfigurationSelector.writeRelativeHeadRowIndex());
        } else {
            currentTableHolder.setWriteRelativeHeadRowIndex(table.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(table);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (table.getCustomWriteHandlerList() != null && !table.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(table.getCustomWriteHandlerList());
        }
        handlerList.addAll(currentConfigurationSelector.writeHandlerList());
        currentTableHolder.setWriteHandlerList(sortAndClearUpHandler(handlerList));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(currentConfigurationSelector.converterMap());
        if (table.getCustomConverterMap() != null && !table.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(table.getCustomConverterMap());
        }
        currentTableHolder.setConverterMap(converterMap);
        currentConfigurationSelector = currentTableHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfigurationSelector is currentTableHolder");
        }
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private void compatibleOldCode(com.alibaba.excel.metadata.Table table) {
        if (table.getTableStyle() != null) {
            final TableStyle tableStyle = table.getTableStyle();
            if (table.getCustomWriteHandlerList() == null) {
                table.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            CellStyle headCellStyle = new CellStyle();
            headCellStyle.setFont(tableStyle.getTableHeadFont());
            headCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            CellStyle contentCellStyle = new CellStyle();
            contentCellStyle.setFont(tableStyle.getTableContentFont());
            contentCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            table.getCustomWriteHandlerList().add(new RowCellStyleStrategy(headCellStyle, contentCellStyle));
        }
    }

    @Override
    public ConfigurationSelector currentConfigurationSelector() {
        return currentConfigurationSelector;
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
            if (currentWorkbookHolder.getOutputStream() != null) {
                currentWorkbookHolder.getOutputStream().close();
            }
            if (currentWorkbookHolder.getTemplateInputStream() != null) {
                currentWorkbookHolder.getTemplateInputStream().close();
            }
        } catch (IOException e) {
            throw new ExcelGenerateException("Can not close IO", e);
        }
    }
}
