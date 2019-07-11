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
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.POITempFile;
import com.alibaba.excel.util.WorkBookUtil;

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
        for (int i = 0; i < data.size(); i++) {
            int n = i + rowNum + 1;
            addOneRowOfDataToExcel(data.get(i), n);
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

    private void addOneRowOfDataToExcel(Object oneRowData, int n) {
        Row row = WorkBookUtil.createRow(context.currentSheetHolder().getSheet(), n);
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List)oneRowData, row);
        } else {
            addJavaObjectToExcel(oneRowData, row);
        }
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        for (int i = 0; i < oneRowData.size(); i++) {
            Cell cell = WorkBookUtil.createCell(row, i, null);
            Object value = oneRowData.get(i);
            converterAndSet(context.currentConfigurationSelector(), value.getClass(), cell, value, null);
        }
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row) {
        ConfigurationSelector currentConfigurationSelector = context.currentConfigurationSelector();
        int i = 0;
        BeanMap beanMap = BeanMap.create(oneRowData);
        for (ExcelColumnProperty excelHeadProperty : currentConfigurationSelector.excelHeadProperty()
            .getColumnPropertyList()) {
            Cell cell = WorkBookUtil.createCell(row, i, null);
            Object value = beanMap.get(excelHeadProperty.getField().getName());
            converterAndSet(currentConfigurationSelector, excelHeadProperty.getField().getType(), cell, value,
                excelHeadProperty);
            i++;
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

        CellData cellData = null;
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
                cell.setCellValue(cell.getStringCellValue());
                return;
            case BOOLEAN:
                cell.setCellValue(cell.getBooleanCellValue());
                return;
            case NUMBER:
                cell.setCellValue(cell.getNumericCellValue());
                return;
            default:
                throw new ExcelDataConvertException("Not supported data:" + value + " return type:" + cell.getCellType()
                    + "at row:" + cell.getRow().getRowNum());
        }
    }
}
