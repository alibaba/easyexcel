package com.alibaba.excel.metadata.csv;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.excel.metadata.data.FormulaData;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellBase;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * csv cell
 *
 * @author Jiaju Zhuang
 */
public class CsvCell extends CellBase {

    /**
     * column index
     */
    private Integer columnIndex;

    /**
     * cell type
     */
    private CellType cellType;
    /**
     * row
     */
    private final CsvRow csvRow;

    /**
     * {@link CellType#NUMERIC}
     */
    private BigDecimal numberValue;
    /**
     * {@link CellType#STRING} and {@link CellType#ERROR} {@link CellType#FORMULA}
     */
    private String stringValue;
    /**
     * {@link CellType#BOOLEAN}
     */
    private Boolean booleanValue;

    /**
     * {@link CellType#NUMERIC}
     */
    private LocalDateTime dateValue;

    /**
     * formula
     */
    private FormulaData formulaData;

    /**
     * rich text string
     */
    private RichTextString richTextString;

    /**
     * style
     */
    private CellStyle cellStyle;

    public CsvCell(CsvRow csvRow, Integer columnIndex, CellType cellType) {
        this.csvRow = csvRow;
        this.columnIndex = columnIndex;
        this.cellType = cellType;
    }

    @Override
    protected void setCellTypeImpl(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    protected void setCellFormulaImpl(String formula) {
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue(formula);
        this.formulaData = formulaData;
    }

    @Override
    protected void removeFormulaImpl() {
        this.formulaData = null;
    }

    @Override
    protected void setCellValueImpl(double value) {
        numberValue = BigDecimal.valueOf(value);
    }

    @Override
    protected void setCellValueImpl(Date value) {
        if (value == null) {
            return;
        }
        this.dateValue = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    @Override
    protected void setCellValueImpl(LocalDateTime value) {
        this.dateValue = value;
    }

    @Override
    protected void setCellValueImpl(Calendar value) {
        if (value == null) {
            return;
        }
        this.dateValue = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    @Override
    protected void setCellValueImpl(String value) {
        this.stringValue = value;
    }

    @Override
    protected void setCellValueImpl(RichTextString value) {
        richTextString = value;
    }

    @Override
    protected SpreadsheetVersion getSpreadsheetVersion() {
        return null;
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public int getRowIndex() {
        return csvRow.getRowNum();
    }

    @Override
    public Sheet getSheet() {
        return csvRow.getSheet();
    }

    @Override
    public Row getRow() {
        return csvRow;
    }

    @Override
    public CellType getCellType() {
        return cellType;
    }

    @Override
    public CellType getCachedFormulaResultType() {
        return getCellType();
    }

    @Override
    public CellType getCachedFormulaResultTypeEnum() {
        return getCellType();
    }

    @Override
    public String getCellFormula() {
        if (formulaData == null) {
            return null;
        }
        return formulaData.getFormulaValue();
    }

    @Override
    public double getNumericCellValue() {
        if (numberValue == null) {
            return 0;
        }
        return numberValue.doubleValue();
    }

    @Override
    public Date getDateCellValue() {
        if (dateValue == null) {
            return null;
        }
        return Date.from(dateValue.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDateTime getLocalDateTimeCellValue() {
        return dateValue;
    }

    @Override
    public RichTextString getRichStringCellValue() {
        return richTextString;
    }

    @Override
    public String getStringCellValue() {
        return stringValue;
    }

    @Override
    public void setCellValue(boolean value) {
        this.booleanValue = value;
    }

    @Override
    public void setCellErrorValue(byte value) {
        this.numberValue = BigDecimal.valueOf(value);
    }

    @Override
    public boolean getBooleanCellValue() {
        return booleanValue;
    }

    @Override
    public byte getErrorCellValue() {
        if (numberValue == null) {
            return 0;
        }
        return numberValue.byteValue();
    }

    @Override
    public void setCellStyle(CellStyle style) {
        this.cellStyle = style;
    }

    @Override
    public CellStyle getCellStyle() {
        return cellStyle;
    }

    @Override
    public void setAsActiveCell() {

    }

    @Override
    public void setCellComment(Comment comment) {

    }

    @Override
    public Comment getCellComment() {
        return null;
    }

    @Override
    public void removeCellComment() {

    }

    @Override
    public Hyperlink getHyperlink() {
        return null;
    }

    @Override
    public void setHyperlink(Hyperlink link) {

    }

    @Override
    public void removeHyperlink() {

    }

    @Override
    public CellRangeAddress getArrayFormulaRange() {
        return null;
    }

    @Override
    public boolean isPartOfArrayFormulaGroup() {
        return false;
    }
}
