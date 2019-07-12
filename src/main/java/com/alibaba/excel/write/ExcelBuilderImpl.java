package com.alibaba.excel.write;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.context.WriteContextImpl;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.POITempFile;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;

import net.sf.cglib.beans.BeanMap;

/**
 * @author jipengfei
 */
public class ExcelBuilderImpl implements ExcelBuilder {

    private WriteContext context;

    public ExcelBuilderImpl(com.alibaba.excel.metadata.Workbook workbook) {
        // 初始化时候创建临时缓存目录，用于规避POI在并发写bug
        POITempFile.createPOIFilesDirectory();
        context = new WriteContextImpl(workbook);
    }

    private void doAddContent(List data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        org.apache.poi.ss.usermodel.Sheet currentSheet = context.currentSheetHolder().getSheet();
        int rowNum = currentSheet.getLastRowNum();
        if (context.currentConfigurationSelector().isNew()) {
            rowNum += context.currentConfigurationSelector().writeRelativeHeadRowIndex();
        }
        for (int relativeRowIndex = 0; relativeRowIndex < data.size(); relativeRowIndex++) {
            int n = relativeRowIndex + rowNum + 1;
            addOneRowOfDataToExcel(data.get(relativeRowIndex), n, relativeRowIndex);
        }
    }

    @Override
    public void addContent(List data, Sheet sheetParam) {
        addContent(data, sheetParam, null);
    }

    @Override
    public void addContent(List data, Sheet sheetParam, Table table) {
        context.currentSheet(sheetParam);
        context.currentTable(table);
        doAddContent(data);
    }

    @Override
    public void finish() {
        context.finish();
    }

    @Override
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        context.currentSheetHolder().getSheet().addMergedRegion(cra);
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n, int relativeRowIndex) {
        beforeRowCreate(n, relativeRowIndex);
        Row row = WorkBookUtil.createRow(context.currentSheetHolder().getSheet(), n);
        afterRowCreate(row, relativeRowIndex);
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List)oneRowData, row, relativeRowIndex);
        } else {
            addJavaObjectToExcel(oneRowData, row, relativeRowIndex);
        }
    }

    private void beforeRowCreate(int rowIndex, int relativeRowIndex) {
        List<WriteHandler> handlerList =
            context.currentConfigurationSelector().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(context.currentSheetHolder(),
                    context.currentTableHolder(), rowIndex, relativeRowIndex, false);
            }
        }
    }

    private void afterRowCreate(Row row, int relativeRowIndex) {
        List<WriteHandler> handlerList =
            context.currentConfigurationSelector().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowCreate(context.currentSheetHolder(),
                    context.currentTableHolder(), row, relativeRowIndex, false);
            }
        }
        if (null != context.currentWorkbookHolder().getWriteHandler()) {
            context.currentWorkbookHolder().getWriteHandler().row(row.getRowNum(), row);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row, int relativeRowIndex) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        List<Head> headList = context.currentConfigurationSelector().excelHeadProperty().getHeadList();
        for (int i = 0; i < oneRowData.size(); i++) {
            Head head = i <= headList.size() ? headList.get(i) : null;
            beforeCellCreate(row, head, relativeRowIndex);
            Cell cell = WorkBookUtil.createCell(row, i);
            Object value = oneRowData.get(i);
            converterAndSet(context.currentConfigurationSelector(), value.getClass(), cell, value, null);
            afterCellCreate(head, cell, relativeRowIndex);
        }
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row, int relativeRowIndex) {
        ConfigurationSelector currentConfigurationSelector = context.currentConfigurationSelector();
        int i = 0;
        BeanMap beanMap = BeanMap.create(oneRowData);
        List<Head> headList = context.currentConfigurationSelector().excelHeadProperty().getHeadList();
        for (ExcelColumnProperty excelHeadProperty : currentConfigurationSelector.excelHeadProperty()
            .getColumnPropertyList()) {
            Head head = i <= headList.size() ? headList.get(i) : null;
            beforeCellCreate(row, head, relativeRowIndex);
            Cell cell = WorkBookUtil.createCell(row, i);
            Object value = beanMap.get(excelHeadProperty.getField().getName());
            converterAndSet(currentConfigurationSelector, excelHeadProperty.getField().getType(), cell, value,
                excelHeadProperty);
            afterCellCreate(head, cell, relativeRowIndex);
            i++;
        }
    }

    private void beforeCellCreate(Row row, Head head, int relativeRowIndex) {
        List<WriteHandler> handlerList =
            context.currentConfigurationSelector().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).beforeCellCreate(context.currentSheetHolder(),
                    context.currentTableHolder(), row, head, relativeRowIndex, false);
            }
        }

    }

    private void afterCellCreate(Head head, Cell cell, int relativeRowIndex) {
        List<WriteHandler> handlerList =
            context.currentConfigurationSelector().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellCreate(context.currentSheetHolder(),
                    context.currentTableHolder(), cell, head, relativeRowIndex, false);
            }
        }
        if (null != context.currentWorkbookHolder().getWriteHandler()) {
            context.currentWorkbookHolder().getWriteHandler().cell(cell.getRowIndex(), cell);
        }
    }

    private void converterAndSet(ConfigurationSelector currentConfigurationSelector, Class clazz, Cell cell,
        Object value, ExcelColumnProperty excelHeadProperty) {
        if (value == null) {
            return;
        }
        Converter converter = currentConfigurationSelector.converterMap().get(excelHeadProperty.getField().getType());
        if (converter == null) {
            throw new ExcelDataConvertException(
                "Can not find 'Converter' support class " + clazz.getSimpleName() + ".");
        }

        CellData cellData;
        try {
            cellData = converter.convertToExcelData(value, excelHeadProperty);
        } catch (Exception e) {
            throw new ExcelDataConvertException("Convert data:" + value + " error,at row:" + cell.getRow().getRowNum(),
                e);
        }
        if (cellData == null || cellData.getType() == null) {
            throw new ExcelDataConvertException(
                "Convert data:" + value + " return null,at row:" + cell.getRow().getRowNum());
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
