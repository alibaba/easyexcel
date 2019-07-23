package com.alibaba.excel.util;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.write.metadata.holder.WorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 
 * @author jipengfei
 */
public class WorkBookUtil {

    public static Workbook createWorkBook(WorkbookHolder workbookHolder)
        throws IOException, InvalidFormatException {
        if (ExcelTypeEnum.XLSX.equals(workbookHolder.getExcelType())) {
            if (workbookHolder.getFile() != null) {
                return new SXSSFWorkbook(new XSSFWorkbook(workbookParam.getFile()));
            }
            if (workbookParam.getInputStream() != null) {
                return new SXSSFWorkbook(new XSSFWorkbook(workbookParam.getInputStream()));
            }
            return new SXSSFWorkbook(500);
        }
        if (workbookParam.getFile() != null) {
            return new HSSFWorkbook(new POIFSFileSystem(workbookParam.getFile()));
        }
        if (workbookParam.getInputStream() != null) {
            return new HSSFWorkbook(new POIFSFileSystem(workbookParam.getInputStream()));
        }
        return new HSSFWorkbook();
    }

    public static Sheet createSheet(Workbook workbook, com.alibaba.excel.write.metadata.Sheet sheet) {
        return workbook.createSheet(sheet.getSheetName() != null ? sheet.getSheetName() : sheet.getSheetNo() + "");
    }

    public static Row createRow(Sheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    public static Cell createCell(Row row, int colNum) {
        return row.createCell(colNum);
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
