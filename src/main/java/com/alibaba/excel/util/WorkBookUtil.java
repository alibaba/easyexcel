package com.alibaba.excel.util;

import java.io.IOException;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.read.builder.Sheets;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author jipengfei
 */
public class WorkBookUtil {

    public WorkBookUtil() {}


    public static void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException {

            Sheets.createWorkBook();

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

    public static void fillDataFormat(WriteCellData<?> cellData, String format, String defaultFormat) {
        if (cellData.getWriteCellStyle() == null) {
            cellData.setWriteCellStyle(new WriteCellStyle());
        }
        if (cellData.getWriteCellStyle().getDataFormatData() == null) {
            cellData.getWriteCellStyle().setDataFormatData(new DataFormatData());
        }
        if (cellData.getWriteCellStyle().getDataFormatData().getFormat() == null) {
            if (format == null) {
                cellData.getWriteCellStyle().getDataFormatData().setFormat(defaultFormat);
            } else {
                cellData.getWriteCellStyle().getDataFormatData().setFormat(format);
            }
        }
    }
}
