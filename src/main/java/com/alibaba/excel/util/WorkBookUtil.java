package com.alibaba.excel.util;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 
 * @author jipengfei
 */
public class WorkBookUtil {

    public static Workbook createWorkBook(com.alibaba.excel.metadata.Workbook workbookParam) throws IOException {
        Workbook workbook;
        if (ExcelTypeEnum.XLS.equals(workbookParam.getExcelType())) {
            workbook = (workbookParam.getTemplateInputStream() == null) ? new HSSFWorkbook()
                : new HSSFWorkbook(new POIFSFileSystem(workbookParam.getTemplateInputStream()));
        } else {
            workbook = (workbookParam.getTemplateInputStream() == null) ? new SXSSFWorkbook(500)
                : new SXSSFWorkbook(new XSSFWorkbook(workbookParam.getTemplateInputStream()));
        }
        return workbook;
    }

    public static Sheet createSheet(Workbook workbook, com.alibaba.excel.metadata.Sheet sheet) {
        return workbook.createSheet(sheet.getSheetName() != null ? sheet.getSheetName() : sheet.getSheetNo() + "");
    }

    public static Row createRow(Sheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle) {
        Cell cell = row.createCell(colNum);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle, String cellValue) {
        Cell cell = createCell(row, colNum, cellStyle);
        cell.setCellValue(cellValue);
        return cell;
    }

    public static Cell createCell(Row row, int colNum, String cellValue) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(cellValue);
        return cell;
    }
}
