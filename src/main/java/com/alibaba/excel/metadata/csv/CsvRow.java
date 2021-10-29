package com.alibaba.excel.metadata.csv;

import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * csv row
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvRow implements Row {

    /**
     * cell list
     */
    private final List<CsvCell> cellList;

    /**
     * workbook
     */
    private final CsvWorkbook csvWorkbook;

    /**
     * sheet
     */
    private final CsvSheet csvSheet;

    /**
     * row index
     */
    private Integer rowIndex;

    /**
     * style
     */
    private CellStyle cellStyle;

    public CsvRow(CsvWorkbook csvWorkbook, CsvSheet csvSheet, Integer rowIndex) {
        cellList = Lists.newArrayList();
        this.csvWorkbook = csvWorkbook;
        this.csvSheet = csvSheet;
        this.rowIndex = rowIndex;
    }

    @Override
    public Cell createCell(int column) {
        CsvCell cell = new CsvCell(csvWorkbook, csvSheet, this, column, null);
        cellList.add(cell);
        return cell;
    }

    @Override
    public Cell createCell(int column, CellType type) {
        CsvCell cell = new CsvCell(csvWorkbook, csvSheet, this, column, type);
        cellList.add(cell);
        return cell;
    }

    @Override
    public void removeCell(Cell cell) {
        cellList.remove(cell);
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowIndex = rowNum;
    }

    @Override
    public int getRowNum() {
        return rowIndex;
    }

    @Override
    public Cell getCell(int cellnum) {
        if (cellnum >= cellList.size()) {
            return null;
        }
        return cellList.get(cellnum - 1);
    }

    @Override
    public Cell getCell(int cellnum, MissingCellPolicy policy) {
        return getCell(cellnum);
    }

    @Override
    public short getFirstCellNum() {
        if (CollectionUtils.isEmpty(cellList)) {
            return -1;
        }
        return 0;
    }

    @Override
    public short getLastCellNum() {
        if (CollectionUtils.isEmpty(cellList)) {
            return -1;
        }
        return (short)cellList.size();
    }

    @Override
    public int getPhysicalNumberOfCells() {
        return getRowNum();
    }

    @Override
    public void setHeight(short height) {

    }

    @Override
    public void setZeroHeight(boolean zHeight) {

    }

    @Override
    public boolean getZeroHeight() {
        return false;
    }

    @Override
    public void setHeightInPoints(float height) {

    }

    @Override
    public short getHeight() {
        return 0;
    }

    @Override
    public float getHeightInPoints() {
        return 0;
    }

    @Override
    public boolean isFormatted() {
        return false;
    }

    @Override
    public CellStyle getRowStyle() {
        return cellStyle;
    }

    @Override
    public void setRowStyle(CellStyle style) {
        this.cellStyle = style;
    }

    @Override
    public Iterator<Cell> cellIterator() {
        return (Iterator<Cell>)(Iterator<? extends Cell>)cellList.iterator();
    }

    @Override
    public Sheet getSheet() {
        return csvSheet;
    }

    @Override
    public int getOutlineLevel() {
        return 0;
    }

    @Override
    public void shiftCellsRight(int firstShiftColumnIndex, int lastShiftColumnIndex, int step) {

    }

    @Override
    public void shiftCellsLeft(int firstShiftColumnIndex, int lastShiftColumnIndex, int step) {

    }

    @Override
    public Iterator<Cell> iterator() {
        return cellIterator();
    }
}
