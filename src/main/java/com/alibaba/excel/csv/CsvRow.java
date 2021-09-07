package com.alibaba.excel.csv;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * TODO
 *
 * @author Jiaju Zhuang
 */
public class CsvRow implements Row {


    @Override
    public Cell createCell(int column) {
        return new CsvCell();
    }

    @Override
    public Cell createCell(int column, CellType type) {
        return null;
    }

    @Override
    public void removeCell(Cell cell) {

    }

    @Override
    public void setRowNum(int rowNum) {

    }

    @Override
    public int getRowNum() {
        return 0;
    }

    @Override
    public Cell getCell(int cellnum) {
        return null;
    }

    @Override
    public Cell getCell(int cellnum, MissingCellPolicy policy) {
        return null;
    }

    @Override
    public short getFirstCellNum() {
        return 0;
    }

    @Override
    public short getLastCellNum() {
        return 0;
    }

    @Override
    public int getPhysicalNumberOfCells() {
        return 0;
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
        return null;
    }

    @Override
    public void setRowStyle(CellStyle style) {

    }

    @Override
    public Iterator<Cell> cellIterator() {
        return null;
    }

    @Override
    public Sheet getSheet() {
        return null;
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
        return null;
    }
}
