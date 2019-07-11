package com.alibaba.excel.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

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
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * Final output stream
     */
    private InputStream templateInputStream;

    public WriteContextImpl(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum excelType,
        Boolean needHead, Map<Class, Converter> customConverterMap, List<WriteHandler> customWriteHandlerList) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin to Initialization 'WriteContextImpl'");
        }
        initCurrentWorkbookHolder(needHead, customConverterMap, customWriteHandlerList);
        this.excelType = excelType;
        this.outputStream = outputStream;
        this.templateInputStream = templateInputStream;
        try {
            currentWorkbookHolder.setWorkbook(WorkBookUtil.createWorkBook(templateInputStream, excelType));
        } catch (IOException e) {
            throw new ExcelGenerateException("Create workbook failure", e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'WriteContextImpl' complete");
        }
    }

    private void initCurrentWorkbookHolder(Boolean needHead, Map<Class, Converter> customConverterMap,
        List<WriteHandler> customWriteHandlerList) {
        currentWorkbookHolder = new WorkbookHolder();
        if (needHead == null) {
            currentWorkbookHolder.setNeedHead(Boolean.TRUE);
        } else {
            currentWorkbookHolder.setNeedHead(needHead);
        }
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (customWriteHandlerList != null && !customWriteHandlerList.isEmpty()) {
            handlerList.addAll(customWriteHandlerList);
        }
        handlerList.addAll(loadDefaultHandler());
        currentWorkbookHolder.setWriteHandlerList(sortAndClearUpHandler(handlerList));
        Map<Class, Converter> converterMap = loadDefaultConverter();
        if (customConverterMap != null && !customConverterMap.isEmpty()) {
            converterMap.putAll(customConverterMap);
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
        Map<Integer, List<WriteHandler>> orderExcelWriteHandlerMap =
            new TreeMap<Integer, List<WriteHandler>>();
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

    private List<WriteHandler> loadDefaultHandler() {
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        CellStyle headCellStyle = new CellStyle();
        Font font = new Font();
        headCellStyle.setFont(font);
        font.setFontName("宋体");
        font.setBold(true);
        headCellStyle.setIndexedColors(IndexedColors.GREY_25_PERCENT);
        handlerList.add(new RowCellStyleStrategy(headCellStyle, new ArrayList<CellStyle>()));
        handlerList.add(new SimpleColumnWidthStyleStrategy(20));
        return handlerList;
    }

    private Map<Class, Converter> loadDefaultConverter() {
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>();
        DateStringConverter dateStringConverter = new DateStringConverter();
        converterMap.put(dateStringConverter.supportJavaTypeKey(), dateStringConverter);
        BigDecimalNumberConverter bigDecimalNumberConverter = new BigDecimalNumberConverter();
        converterMap.put(bigDecimalNumberConverter.supportJavaTypeKey(), bigDecimalNumberConverter);
        return converterMap;
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
            currentConfigurationSelector = currentSheetHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfigurationSelector is currentSheetHolder");
            }
            return;
        }
        initCurrentSheetHolder(sheet);
        // Initialization current sheet
        initCurrentSheet(sheet);
    }

    private void initCurrentSheetHolder(com.alibaba.excel.metadata.Sheet sheet) {
        currentSheetHolder = new SheetHolder();
        currentSheetHolder.setSheetParam(sheet);
        if (sheet.getNeedHead() == null) {
            currentSheetHolder.setNeedHead(currentConfigurationSelector.needHead());
        } else {
            currentSheetHolder.setNeedHead(sheet.getNeedHead());
        }
        if (sheet.getWriteRelativeHeadRowIndex() == null) {
            currentSheetHolder.setWriteRelativeHeadRowIndex(currentConfigurationSelector.writeRelativeHeadRowIndex());
        } else {
            currentSheetHolder.setNeedHead(sheet.getWriteRelativeHeadRowIndex());
        }

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
        currentWorkbookHolder.setConverterMap(converterMap);
        currentSheetHolder.setHasBeenInitializedTable(new HashMap<Integer, TableHolder>());
        currentWorkbookHolder.getHasBeenInitializedSheet().put(sheet.getSheetNo(), currentSheetHolder);
        currentConfigurationSelector = currentSheetHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfigurationSelector is currentSheetHolder");
        }
    }

    private void initCurrentSheet(com.alibaba.excel.metadata.Sheet sheet) {
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
        initHead();
    }

    public void initHead(ExcelHeadProperty excelHeadPropert) {
        if (!currentConfigurationSelector.needHead() || !currentSheetHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int startRow = getCurrentSheet().getLastRowNum();
        startRow += currentConfigurationSelector.writeRelativeHeadRowIndex();
        // Combined head
        addMergedRegionToCurrentSheet(startRow);
        for (int i = startRow; i < currentSheetHolder.getExcelHeadProperty().getHeadRowNumber() + startRow; i++) {
            Row row = WorkBookUtil.createRow(getCurrentSheet(), i);
            // Set the row height of the header
            currentSheetHolder.getRowHighStyleStrategy().headColumnHigh(i);
            if (null != writeHandler) {
                this.writeHandler.row(i, row);
            }
            addOneRowOfHeadDataToExcel(row, i, currentSheetHolder.getExcelHeadProperty().getHeadList());
        }
    }

    public void initTableHead() {
        if (!currentTableHolder.isNeedHead() || !currentTableHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int startRow = getCurrentSheet().getLastRowNum();
        if (startRow > 0) {
            startRow += 4;
        } else {
            startRow = getCurrentSheetParam().getStartRow();
        }
        // Combined head
        addMergedRegionToCurrentSheet(startRow);
        for (int i = startRow; i < currentTableHolder.getExcelHeadProperty().getHeadRowNumber() + startRow; i++) {
            Row row = WorkBookUtil.createRow(getCurrentSheet(), i);
            // Set the row height of the header
            currentTableHolder.getRowHighStyleStrategy().headColumnHigh(i);
            if (null != writeHandler) {
                this.writeHandler.row(i, row);
            }
            addOneRowOfHeadDataToExcel(row, i, currentTableHolder.getExcelHeadProperty().getHeadList());
        }
        // Initialization sheet column width
        initSheetColumnWidth();
    }

    private void addMergedRegionToCurrentSheet(int startRow) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : currentSheetHolder.getExcelHeadProperty()
            .getCellRangeModels()) {
            currentSheet.addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + startRow,
                cellRangeModel.getLastRow() + startRow, cellRangeModel.getFirstCol(), cellRangeModel.getLastCol()));
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, int rowIndex, List<Head> headList) {
        for (int i = 0; i < headList.size(); i++) {
            Head head = headList.get(i);
            Cell cell = WorkBookUtil.createCell(row, i,
                currentSheetHolder.getCellStyleStrategy().headCellStyle(rowIndex, headList.get(i)),
                head.getHeadName(i));
            if (null != writeHandler) {
                this.writeHandler.cell(i, cell);
            }
        }
    }

    private void cleanCurrentTable() {
        this.excelHeadProperty = null;
        this.currentHeadCellStyle = null;
        this.currentContentCellStyle = null;
        this.currentTable = null;

    }

    @Override
    public void currentTable(Table table) {
        if (table == null) {
            return;
        }
        if (currentSheetHolder == null) {
            throw new IllegalStateException("Must first call WriteContextImpl#currentSheet");
        }
        if(currentSheetHolder.getHasBeenInitializedTable().containsKey(table.getTableNo())){
            currentTableHolder=currentSheetHolder.getHasBeenInitializedTable().get(table.getTableNo());
            return;
        }


        
        
        currentTable.getTableNo()


        if (null == currentTable || currentTable.getTableNo() != table.getTableNo()) {
            cleanCurrentTable();
            this.currentTable = table;
            this.initExcelHeadProperty(table.getHead(), table.getClazz());
            this.initTableStyle(table.getTableStyle());
            this.initTableHead();
        }

    }

    @Override
    public ExcelHeadProperty getExcelHeadProperty() {
        return currentSheetHolder.getExcelHeadProperty();
    }

    @Override
    public boolean needHead() {
        return this.needHead;
    }

    public String getCurrentSheetName() {
        return currentSheetHolder.getCurrentSheetParam();
    }

    public void setCurrentSheetName(String currentSheetName) {
        this.currentSheetName = currentSheetName;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public CellStyle getCurrentHeadCellStyle() {
        return this.currentHeadCellStyle == null ? defaultCellStyle : this.currentHeadCellStyle;
    }

    public CellStyle getCurrentContentStyle() {
        return this.currentContentCellStyle;
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    public com.alibaba.excel.metadata.Sheet getCurrentSheetParam() {
        return currentSheetHolder.getCurrentSheetParam();
    }

    public void setCurrentSheetParam(com.alibaba.excel.metadata.Sheet currentSheetParam) {
        this.currentSheetParam = currentSheetParam;
    }

    public Table getCurrentTableParam() {
        return currentTableHolder.getCurrentTableParam();
    }

    public Table getCurrentTable() {
        return currentTable;
    }

    @Override
    public ConverterRegistryCenter getConverterRegistryCenter() {
        return registryCenter;
    }

    @Override
    public void finish() {
        try {
            workbook.write(outputStream);
            workbook.close();
            if (outputStream != null) {
                outputStream.close();
            }
            if (templateInputStream != null) {
                templateInputStream.close();
            }
        } catch (IOException e) {
            throw new ExcelGenerateException("Can not close IO", e);
        }
    }
}
