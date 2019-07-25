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

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * 
 * @author jipengfei
 */
public class WorkBookUtil {

    public static Workbook createWorkBook(WriteWorkbookHolder writeWorkbookHolder)
        throws IOException, InvalidFormatException {
        if (ExcelTypeEnum.XLSX.equals(writeWorkbookHolder.getExcelType())) {
            if (writeWorkbookHolder.getTemplateFile() != null) {
                return new SXSSFWorkbook(new XSSFWorkbook(writeWorkbookHolder.getTemplateFile()));
            }
            if (writeWorkbookHolder.getTemplateInputStream() != null) {
                return new SXSSFWorkbook(new XSSFWorkbook(writeWorkbookHolder.getTemplateInputStream()));
            }
            return new SXSSFWorkbook(500);
        }
        if (writeWorkbookHolder.getTemplateFile() != null) {
            return new HSSFWorkbook(new POIFSFileSystem(writeWorkbookHolder.getTemplateFile()));
        }
        if (writeWorkbookHolder.getTemplateInputStream() != null) {
            return new HSSFWorkbook(new POIFSFileSystem(writeWorkbookHolder.getTemplateInputStream()));
        }
        return new HSSFWorkbook();
    }

    public static Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
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
