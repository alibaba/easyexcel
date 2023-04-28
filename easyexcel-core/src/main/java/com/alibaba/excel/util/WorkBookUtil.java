package com.alibaba.excel.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.alibaba.excel.metadata.csv.CsvWorkbook;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author jipengfei
 */
public class WorkBookUtil {

    private WorkBookUtil() {}

    public static void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException {
        switch (writeWorkbookHolder.getExcelType()) {
            case XLSX:
                if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
                    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(writeWorkbookHolder.getTempTemplateInputStream());
                    writeWorkbookHolder.setCachedWorkbook(xssfWorkbook);
                    if (writeWorkbookHolder.getInMemory()) {
                        writeWorkbookHolder.setWorkbook(xssfWorkbook);
                    } else {
                        writeWorkbookHolder.setWorkbook(new SXSSFWorkbook(xssfWorkbook));
                    }
                    return;
                }
                Workbook workbook;
                if (writeWorkbookHolder.getInMemory()) {
                    workbook = new XSSFWorkbook();
                } else {
                    workbook = new SXSSFWorkbook();
                }
                writeWorkbookHolder.setCachedWorkbook(workbook);
                writeWorkbookHolder.setWorkbook(workbook);
                return;
            case XLS:
                HSSFWorkbook hssfWorkbook;
                if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
                    hssfWorkbook = new HSSFWorkbook(
                        new POIFSFileSystem(writeWorkbookHolder.getTempTemplateInputStream()));
                } else {
                    hssfWorkbook = new HSSFWorkbook();
                }
                writeWorkbookHolder.setCachedWorkbook(hssfWorkbook);
                writeWorkbookHolder.setWorkbook(hssfWorkbook);
                if (writeWorkbookHolder.getPassword() != null) {
                    Biff8EncryptionKey.setCurrentUserPassword(writeWorkbookHolder.getPassword());
                    hssfWorkbook.writeProtectWorkbook(writeWorkbookHolder.getPassword(), StringUtils.EMPTY);
                }
                return;
            case CSV:
                CsvWorkbook csvWorkbook = new CsvWorkbook(new PrintWriter(
                    new OutputStreamWriter(writeWorkbookHolder.getOutputStream(), writeWorkbookHolder.getCharset())),
                    writeWorkbookHolder.getGlobalConfiguration().getLocale(),
                    writeWorkbookHolder.getGlobalConfiguration().getUse1904windowing(),
                    writeWorkbookHolder.getGlobalConfiguration().getUseScientificFormat(),
                    writeWorkbookHolder.getCharset(),
                    writeWorkbookHolder.getWithBom());
                writeWorkbookHolder.setCachedWorkbook(csvWorkbook);
                writeWorkbookHolder.setWorkbook(csvWorkbook);
                return;
            default:
                throw new UnsupportedOperationException("Wrong excel type.");
        }

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
