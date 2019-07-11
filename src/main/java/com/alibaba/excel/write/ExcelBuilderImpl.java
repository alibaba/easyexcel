package com.alibaba.excel.write;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.context.WriteContextImpl;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.POITempFile;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.handler.WriteHandler;

import net.sf.cglib.beans.BeanMap;

/**
 * @author jipengfei
 */
public class ExcelBuilderImpl implements ExcelBuilder {

    private WriteContext context;

    public ExcelBuilderImpl(InputStream templateInputStream, OutputStream out, ExcelTypeEnum excelType,
        boolean needHead, Map<Class, Converter> customConverterMap, List<WriteHandler> customWriteHandlerList) {
        // 初始化时候创建临时缓存目录，用于规避POI在并发写bug
        POITempFile.createPOIFilesDirectory();
        context = new WriteContextImpl(templateInputStream, out, excelType, needHead, customConverterMap,
            customWriteHandlerList);
    }

    private void doAddContent(List data, int startRow) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        int rowNum = context.getCurrentSheet().getLastRowNum();
        if (rowNum == 0) {
            Row row = context.getCurrentSheet().getRow(0);
            if (row == null) {
                if (context.getExcelHeadProperty() == null || !context.needHead()) {
                    rowNum = -1;
                }
            }
        }
        if (rowNum < startRow) {
            rowNum = startRow;
        }
        for (int i = 0; i < data.size(); i++) {
            int n = i + rowNum + 1;
            addOneRowOfDataToExcel(data.get(i), n);
        }
    }

    @Override
    public void addContent(List data, Sheet sheetParam) {
        context.currentSheet(sheetParam);
        doAddContent(data, sheetParam.getWriteRelativeHeadRowIndex());
    }

    @Override
    public void addContent(List data, Sheet sheetParam, Table table) {
        context.currentSheet(sheetParam);
        context.currentTable(table);
        doAddContent(data, sheetParam.getWriteRelativeHeadRowIndex());
    }

    @Override
    public void finish() {
        context.finish();
    }

    @Override
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        context.getCurrentSheet().addMergedRegion(cra);
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        for (int i = 0; i < oneRowData.size(); i++) {
            Object cellValue = oneRowData.get(i);
            Cell cell = WorkBookUtil.createCell(row, i, context.getCurrentContentStyle());
            cell = convertValue(cell, cellValue, null);
            if (null != context.getWriteHandler()) {
                context.getWriteHandler().cell(i, cell);
            }
        }
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row) {
        int i = 0;
        BeanMap beanMap = BeanMap.create(oneRowData);
        for (ExcelColumnProperty excelHeadProperty : context.getExcelHeadProperty().getColumnPropertyList()) {
            BaseRowModel baseRowModel = (BaseRowModel)oneRowData;
            CellStyle cellStyle =
                baseRowModel.getStyle(i) != null ? baseRowModel.getStyle(i) : context.getCurrentContentStyle();
            Object value = beanMap.get(excelHeadProperty.getField().getName());
            // excelHeadProperty.getField().getType();

            Cell cell = WorkBookUtil.createCell(row, i, cellStyle);
            cell.setCL cell = convertValue(cell, value, excelHeadProperty);
            if (null != context.getWriteHandler()) {
                context.getWriteHandler().cell(i, cell);
            }
            i++;
        }
    }

    private Cell convertValue(Cell cell, Object value, ExcelColumnProperty excelHeadProperty) {
        if (!CollectionUtils.isEmpty(this.converters)) {
            for (Converter c : this.converters) {
              CellData c.convertToExcelData(excelHeadProperty);

                if (value != null && c.support(value)) {
                    return c.convert(cell, value, excelHeadProperty);
                }
            }
        }
        return cell;
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n) {
        Row row = WorkBookUtil.createRow(context.getCurrentSheet(), n);
        if (null != context.getWriteHandler()) {
            context.getWriteHandler().row(n, row);
        }
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List)oneRowData, row);
        } else {
            addJavaObjectToExcel(oneRowData, row);
        }
    }

}
