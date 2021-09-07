package com.alibaba.excel.csv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * TODO
 *
 * @author Jiaju Zhuang
 */
@Data
public class CsvWorkbook implements Workbook {

    private CsvSheet csvSheet;

    public CsvWorkbook(InputStream inputStream, File file) {
        try {
            this.csvSheet = new CsvSheet(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getActiveSheetIndex() {
        return 0;
    }

    @Override
    public void setActiveSheet(int sheetIndex) {

    }

    @Override
    public int getFirstVisibleTab() {
        return 0;
    }

    @Override
    public void setFirstVisibleTab(int sheetIndex) {

    }

    @Override
    public void setSheetOrder(String sheetname, int pos) {

    }

    @Override
    public void setSelectedTab(int index) {

    }

    @Override
    public void setSheetName(int sheet, String name) {

    }

    @Override
    public String getSheetName(int sheet) {
        return null;
    }

    @Override
    public int getSheetIndex(String name) {
        return 0;
    }

    @Override
    public int getSheetIndex(Sheet sheet) {
        return 0;
    }

    @Override
    public Sheet createSheet() {
        return csvSheet;
    }

    @Override
    public Sheet createSheet(String sheetname) {
        return csvSheet;
    }

    @Override
    public Sheet cloneSheet(int sheetNum) {
        return null;
    }

    @Override
    public Iterator<Sheet> sheetIterator() {
        return null;
    }

    @Override
    public int getNumberOfSheets() {
        return 0;
    }

    @Override
    public Sheet getSheetAt(int index) {
        return null;
    }

    @Override
    public Sheet getSheet(String name) {
        return null;
    }

    @Override
    public void removeSheetAt(int index) {

    }

    @Override
    public Font createFont() {
        return null;
    }

    @Override
    public Font findFont(boolean bold, short color, short fontHeight, String name, boolean italic, boolean strikeout,
        short typeOffset, byte underline) {
        return null;
    }

    @Override
    public short getNumberOfFonts() {
        return 0;
    }

    @Override
    public int getNumberOfFontsAsInt() {
        return 0;
    }

    @Override
    public Font getFontAt(short idx) {
        return null;
    }

    @Override
    public Font getFontAt(int idx) {
        return null;
    }

    @Override
    public CellStyle createCellStyle() {
        return new CsvCellStyle();
    }

    @Override
    public int getNumCellStyles() {
        return 0;
    }

    @Override
    public CellStyle getCellStyleAt(int idx) {
        return null;
    }

    @Override
    public void write(OutputStream stream) throws IOException {
        csvSheet.flushData();
        csvSheet.csvPrinter.flush();
        csvSheet.csvPrinter.close();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int getNumberOfNames() {
        return 0;
    }

    @Override
    public Name getName(String name) {
        return null;
    }

    @Override
    public List<? extends Name> getNames(String name) {
        return null;
    }

    @Override
    public List<? extends Name> getAllNames() {
        return null;
    }

    @Override
    public Name getNameAt(int nameIndex) {
        return null;
    }

    @Override
    public Name createName() {
        return null;
    }

    @Override
    public int getNameIndex(String name) {
        return 0;
    }

    @Override
    public void removeName(int index) {

    }

    @Override
    public void removeName(String name) {

    }

    @Override
    public void removeName(Name name) {

    }

    @Override
    public int linkExternalWorkbook(String name, Workbook workbook) {
        return 0;
    }

    @Override
    public void setPrintArea(int sheetIndex, String reference) {

    }

    @Override
    public void setPrintArea(int sheetIndex, int startColumn, int endColumn, int startRow, int endRow) {

    }

    @Override
    public String getPrintArea(int sheetIndex) {
        return null;
    }

    @Override
    public void removePrintArea(int sheetIndex) {

    }

    @Override
    public MissingCellPolicy getMissingCellPolicy() {
        return null;
    }

    @Override
    public void setMissingCellPolicy(MissingCellPolicy missingCellPolicy) {

    }

    @Override
    public DataFormat createDataFormat() {
        return new CsvDataFormat();
    }

    @Override
    public int addPicture(byte[] pictureData, int format) {
        return 0;
    }

    @Override
    public List<? extends PictureData> getAllPictures() {
        return null;
    }

    @Override
    public CreationHelper getCreationHelper() {
        return null;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public void setHidden(boolean hiddenFlag) {

    }

    @Override
    public boolean isSheetHidden(int sheetIx) {
        return false;
    }

    @Override
    public boolean isSheetVeryHidden(int sheetIx) {
        return false;
    }

    @Override
    public void setSheetHidden(int sheetIx, boolean hidden) {

    }

    @Override
    public SheetVisibility getSheetVisibility(int sheetIx) {
        return null;
    }

    @Override
    public void setSheetVisibility(int sheetIx, SheetVisibility visibility) {

    }

    @Override
    public void addToolPack(UDFFinder toopack) {

    }

    @Override
    public void setForceFormulaRecalculation(boolean value) {

    }

    @Override
    public boolean getForceFormulaRecalculation() {
        return false;
    }

    @Override
    public SpreadsheetVersion getSpreadsheetVersion() {
        return null;
    }

    @Override
    public int addOlePackage(byte[] oleData, String label, String fileName, String command) throws IOException {
        return 0;
    }

    @Override
    public Iterator<Sheet> iterator() {
        return null;
    }
}
