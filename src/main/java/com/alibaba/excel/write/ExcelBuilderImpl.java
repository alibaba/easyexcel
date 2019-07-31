package com.alibaba.excel.write;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.context.WriteContextImpl;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

import net.sf.cglib.beans.BeanMap;

/**
 * @author jipengfei
 */
public class ExcelBuilderImpl implements ExcelBuilder {

    private WriteContext context;

    public ExcelBuilderImpl(WriteWorkbook writeWorkbook) {
        try {
            // Create temporary cache directory at initialization time to avoid POI concurrent write bugs
            FileUtils.createPoiFilesDirectory();
            context = new WriteContextImpl(writeWorkbook);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelGenerateException(e);
        }
    }

    private void doAddContent(List data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        Sheet currentSheet = context.writeSheetHolder().getSheet();
        int rowNum = currentSheet.getLastRowNum();
        if (context.currentWriteHolder().isNew()) {
            rowNum += context.currentWriteHolder().relativeHeadRowIndex();
        }
        for (int relativeRowIndex = 0; relativeRowIndex < data.size(); relativeRowIndex++) {
            int n = relativeRowIndex + rowNum + 1;
            addOneRowOfDataToExcel(data.get(relativeRowIndex), n, relativeRowIndex);
        }
    }

    @Override
    public void addContent(List data, WriteSheet writeSheet) {
        addContent(data, writeSheet, null);
    }

    @Override
    public void addContent(List data, WriteSheet writeSheet, WriteTable writeTable) {
        try {
            context.currentSheet(writeSheet);
            context.currentTable(writeTable);
            doAddContent(data);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelGenerateException(e);
        }
    }

    @Override
    public void finish() {
        context.finish();
    }

    @Override
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        context.writeSheetHolder().getSheet().addMergedRegion(cra);
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n, int relativeRowIndex) {
        beforeRowCreate(n, relativeRowIndex);
        Row row = WorkBookUtil.createRow(context.writeSheetHolder().getSheet(), n);
        afterRowCreate(row, relativeRowIndex);
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List)oneRowData, row, relativeRowIndex);
        } else {
            addJavaObjectToExcel(oneRowData, row, relativeRowIndex);
        }
    }

    private void beforeRowCreate(int rowIndex, int relativeRowIndex) {
        List<WriteHandler> handlerList = context.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(context.writeSheetHolder(), context.writeTableHolder(),
                    rowIndex, relativeRowIndex, false);
            }
        }
    }

    private void afterRowCreate(Row row, int relativeRowIndex) {
        List<WriteHandler> handlerList = context.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowCreate(context.writeSheetHolder(), context.writeTableHolder(),
                    row, relativeRowIndex, false);
            }
        }
        if (null != context.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()) {
            context.writeWorkbookHolder().getWriteWorkbook().getWriteHandler().row(row.getRowNum(), row);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row, int relativeRowIndex) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        Map<Integer, Head> headMap = context.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
        int dataIndex = 0;
        int cellIndex = 0;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (dataIndex >= oneRowData.size()) {
                return;
            }
            cellIndex = entry.getKey();
            Head head = entry.getValue();
            doAddBasicTypeToExcel(oneRowData, head, row, relativeRowIndex, dataIndex++, cellIndex);
        }
        // Finish
        if (dataIndex >= oneRowData.size()) {
            return;
        }
        if (cellIndex != 0) {
            cellIndex++;
        }
        for (int i = 0; i < oneRowData.size() - dataIndex; i++) {
            doAddBasicTypeToExcel(oneRowData, null, row, relativeRowIndex, dataIndex++, cellIndex++);
        }
    }

    private void doAddBasicTypeToExcel(List<Object> oneRowData, Head head, Row row, int relativeRowIndex, int dataIndex,
        int cellIndex) {
        beforeCellCreate(row, head, relativeRowIndex);
        Cell cell = WorkBookUtil.createCell(row, cellIndex);
        Object value = oneRowData.get(dataIndex);
        converterAndSet(context.currentWriteHolder(), value.getClass(), cell, value, null);
        afterCellCreate(head, cell, relativeRowIndex);
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row, int relativeRowIndex) {
        WriteHolder currentWriteHolder = context.currentWriteHolder();
        BeanMap beanMap = BeanMap.create(oneRowData);
        Set<String> beanMapHandledSet = new HashSet<String>();
        Map<Integer, Head> headMap = context.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMap =
            context.currentWriteHolder().excelWriteHeadProperty().getContentPropertyMap();
        int cellIndex = 0;
        for (Map.Entry<Integer, ExcelContentProperty> entry : contentPropertyMap.entrySet()) {
            cellIndex = entry.getKey();
            ExcelContentProperty excelContentProperty = entry.getValue();
            String name = excelContentProperty.getField().getName();
            if (!beanMap.containsKey(name)) {
                continue;
            }
            Head head = headMap.get(cellIndex);
            beforeCellCreate(row, head, relativeRowIndex);
            Cell cell = WorkBookUtil.createCell(row, cellIndex);
            Object value = beanMap.get(name);
            converterAndSet(currentWriteHolder, excelContentProperty.getField().getType(), cell, value,
                excelContentProperty);
            afterCellCreate(head, cell, relativeRowIndex);
            beanMapHandledSet.add(name);
        }
        // Finish
        if (beanMapHandledSet.size() == beanMap.size()) {
            return;
        }
        if (cellIndex != 0) {
            cellIndex++;
        }
        Set<Map.Entry<String, Object>> entrySet = beanMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null || beanMapHandledSet.contains(entry.getKey())) {
                continue;
            }
            beforeCellCreate(row, null, relativeRowIndex);
            Cell cell = WorkBookUtil.createCell(row, cellIndex++);
            converterAndSet(currentWriteHolder, entry.getValue().getClass(), cell, entry.getValue(), null);
            afterCellCreate(null, cell, relativeRowIndex);
        }
    }

    private void beforeCellCreate(Row row, Head head, int relativeRowIndex) {
        List<WriteHandler> handlerList = context.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).beforeCellCreate(context.writeSheetHolder(),
                    context.writeTableHolder(), row, head, relativeRowIndex, false);
            }
        }

    }

    private void afterCellCreate(Head head, Cell cell, int relativeRowIndex) {
        List<WriteHandler> handlerList = context.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellCreate(context.writeSheetHolder(), context.writeTableHolder(),
                    cell, head, relativeRowIndex, false);
            }
        }
        if (null != context.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()) {
            context.writeWorkbookHolder().getWriteWorkbook().getWriteHandler().cell(cell.getRowIndex(), cell);
        }
    }

    private void converterAndSet(WriteHolder currentWriteHolder, Class clazz, Cell cell, Object value,
        ExcelContentProperty excelContentProperty) {
        if (value == null) {
            return;
        }
        if (value instanceof String && currentWriteHolder.globalConfiguration().getAutoTrim()) {
            value = ((String)value).trim();
        }
        Converter converter = null;
        if (excelContentProperty != null) {
            converter = excelContentProperty.getConverter();
        }
        if (converter == null) {
            converter = currentWriteHolder.converterMap().get(ConverterKeyBuild.buildKey(clazz));
        }
        if (converter == null) {
            throw new ExcelDataConvertException(
                "Can not find 'Converter' support class " + clazz.getSimpleName() + ".");
        }
        CellData cellData;
        try {
            cellData =
                converter.convertToExcelData(value, excelContentProperty, currentWriteHolder.globalConfiguration());
        } catch (Exception e) {
            throw new ExcelDataConvertException("Convert data:" + value + " error,at row:" + cell.getRow().getRowNum(),
                e);
        }
        if (cellData == null || cellData.getType() == null) {
            throw new ExcelDataConvertException(
                "Convert data:" + value + " return null,at row:" + cell.getRow().getRowNum());
        }
        if (cellData.getFormula() != null && cellData.getFormula()) {
            cell.setCellFormula(cellData.getFormulaValue());
        }
        switch (cellData.getType()) {
            case STRING:
                cell.setCellValue(cellData.getStringValue());
                return;
            case BOOLEAN:
                cell.setCellValue(cellData.getBooleanValue());
                return;
            case NUMBER:
                cell.setCellValue(cellData.getDoubleValue());
                return;
            default:
                throw new ExcelDataConvertException("Not supported data:" + value + " return type:" + cell.getCellType()
                    + "at row:" + cell.getRow().getRowNum());
        }
    }
}
