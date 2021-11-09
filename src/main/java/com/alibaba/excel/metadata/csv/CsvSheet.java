package com.alibaba.excel.metadata.csv;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.enums.NumericCellTypeEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.NumberDataFormatterUtils;
import com.alibaba.excel.util.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.AutoFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PaneInformation;

/**
 * csv sheet
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvSheet implements Sheet, Closeable {
    /**
     * workbook
     */
    private CsvWorkbook csvWorkbook;
    /**
     * output
     */
    private Appendable out;
    /**
     * row cache
     */
    private Integer rowCacheCount;
    /**
     * format
     */
    public CSVFormat csvFormat;

    /**
     * last row index
     */
    private Integer lastRowIndex;

    /**
     * row cache
     */
    private List<CsvRow> rowCache;
    /**
     * csv printer
     */
    private CSVPrinter csvPrinter;

    public CsvSheet(CsvWorkbook csvWorkbook, Appendable out) {
        this.csvWorkbook = csvWorkbook;
        this.out = out;
        this.rowCacheCount = 100;
        this.csvFormat = CSVFormat.DEFAULT;
        this.lastRowIndex = -1;
    }

    @Override
    public Row createRow(int rownum) {
        // Initialize the data when the row is first created
        initSheet();

        lastRowIndex++;
        assert rownum == lastRowIndex : "csv create row must be in order.";
        printData();
        CsvRow csvRow = new CsvRow(csvWorkbook, this, rownum);
        rowCache.add(csvRow);
        return csvRow;
    }

    private void initSheet() {
        if (csvPrinter != null) {
            return;
        }
        rowCache = ListUtils.newArrayListWithExpectedSize(rowCacheCount);
        try {
            csvPrinter = csvFormat.print(out);
        } catch (IOException e) {
            throw new ExcelGenerateException(e);
        }
    }

    @Override
    public void removeRow(Row row) {
        throw new UnsupportedOperationException("csv cannot move row.");
    }

    @Override
    public Row getRow(int rownum) {
        int actualRowIndex = rownum - (lastRowIndex - rowCache.size()) - 1;
        if (actualRowIndex < 0 || actualRowIndex > rowCache.size() - 1) {
            throw new UnsupportedOperationException("The current data does not exist or has been flushed to disk\n.");
        }
        return rowCache.get(actualRowIndex);
    }

    @Override
    public int getPhysicalNumberOfRows() {
        return lastRowIndex - rowCache.size();
    }

    @Override
    public int getFirstRowNum() {
        if (lastRowIndex < 0) {
            return -1;
        }
        return 0;
    }

    @Override
    public int getLastRowNum() {
        return lastRowIndex;
    }

    @Override
    public void setColumnHidden(int columnIndex, boolean hidden) {

    }

    @Override
    public boolean isColumnHidden(int columnIndex) {
        return false;
    }

    @Override
    public void setRightToLeft(boolean value) {

    }

    @Override
    public boolean isRightToLeft() {
        return false;
    }

    @Override
    public void setColumnWidth(int columnIndex, int width) {

    }

    @Override
    public int getColumnWidth(int columnIndex) {
        return 0;
    }

    @Override
    public float getColumnWidthInPixels(int columnIndex) {
        return 0;
    }

    @Override
    public void setDefaultColumnWidth(int width) {

    }

    @Override
    public int getDefaultColumnWidth() {
        return 0;
    }

    @Override
    public short getDefaultRowHeight() {
        return 0;
    }

    @Override
    public float getDefaultRowHeightInPoints() {
        return 0;
    }

    @Override
    public void setDefaultRowHeight(short height) {

    }

    @Override
    public void setDefaultRowHeightInPoints(float height) {

    }

    @Override
    public CellStyle getColumnStyle(int column) {
        return null;
    }

    @Override
    public int addMergedRegion(CellRangeAddress region) {
        return 0;
    }

    @Override
    public int addMergedRegionUnsafe(CellRangeAddress region) {
        return 0;
    }

    @Override
    public void validateMergedRegions() {

    }

    @Override
    public void setVerticallyCenter(boolean value) {

    }

    @Override
    public void setHorizontallyCenter(boolean value) {

    }

    @Override
    public boolean getHorizontallyCenter() {
        return false;
    }

    @Override
    public boolean getVerticallyCenter() {
        return false;
    }

    @Override
    public void removeMergedRegion(int index) {

    }

    @Override
    public void removeMergedRegions(Collection<Integer> indices) {

    }

    @Override
    public int getNumMergedRegions() {
        return 0;
    }

    @Override
    public CellRangeAddress getMergedRegion(int index) {
        return null;
    }

    @Override
    public List<CellRangeAddress> getMergedRegions() {
        return null;
    }

    @Override
    public Iterator<Row> rowIterator() {
        return (Iterator<Row>)(Iterator<? extends Row>)rowCache.iterator();
    }

    @Override
    public void setForceFormulaRecalculation(boolean value) {

    }

    @Override
    public boolean getForceFormulaRecalculation() {
        return false;
    }

    @Override
    public void setAutobreaks(boolean value) {

    }

    @Override
    public void setDisplayGuts(boolean value) {

    }

    @Override
    public void setDisplayZeros(boolean value) {

    }

    @Override
    public boolean isDisplayZeros() {
        return false;
    }

    @Override
    public void setFitToPage(boolean value) {

    }

    @Override
    public void setRowSumsBelow(boolean value) {

    }

    @Override
    public void setRowSumsRight(boolean value) {

    }

    @Override
    public boolean getAutobreaks() {
        return false;
    }

    @Override
    public boolean getDisplayGuts() {
        return false;
    }

    @Override
    public boolean getFitToPage() {
        return false;
    }

    @Override
    public boolean getRowSumsBelow() {
        return false;
    }

    @Override
    public boolean getRowSumsRight() {
        return false;
    }

    @Override
    public boolean isPrintGridlines() {
        return false;
    }

    @Override
    public void setPrintGridlines(boolean show) {

    }

    @Override
    public boolean isPrintRowAndColumnHeadings() {
        return false;
    }

    @Override
    public void setPrintRowAndColumnHeadings(boolean show) {

    }

    @Override
    public PrintSetup getPrintSetup() {
        return null;
    }

    @Override
    public Header getHeader() {
        return null;
    }

    @Override
    public Footer getFooter() {
        return null;
    }

    @Override
    public void setSelected(boolean value) {

    }

    @Override
    public double getMargin(short margin) {
        return 0;
    }

    @Override
    public void setMargin(short margin, double size) {

    }

    @Override
    public boolean getProtect() {
        return false;
    }

    @Override
    public void protectSheet(String password) {

    }

    @Override
    public boolean getScenarioProtect() {
        return false;
    }

    @Override
    public void setZoom(int scale) {

    }

    @Override
    public short getTopRow() {
        return 0;
    }

    @Override
    public short getLeftCol() {
        return 0;
    }

    @Override
    public void showInPane(int topRow, int leftCol) {

    }

    @Override
    public void shiftRows(int startRow, int endRow, int n) {

    }

    @Override
    public void shiftRows(int startRow, int endRow, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {

    }

    @Override
    public void shiftColumns(int startColumn, int endColumn, int n) {

    }

    @Override
    public void createFreezePane(int colSplit, int rowSplit, int leftmostColumn, int topRow) {

    }

    @Override
    public void createFreezePane(int colSplit, int rowSplit) {

    }

    @Override
    public void createSplitPane(int xSplitPos, int ySplitPos, int leftmostColumn, int topRow, int activePane) {

    }

    @Override
    public PaneInformation getPaneInformation() {
        return null;
    }

    @Override
    public void setDisplayGridlines(boolean show) {

    }

    @Override
    public boolean isDisplayGridlines() {
        return false;
    }

    @Override
    public void setDisplayFormulas(boolean show) {

    }

    @Override
    public boolean isDisplayFormulas() {
        return false;
    }

    @Override
    public void setDisplayRowColHeadings(boolean show) {

    }

    @Override
    public boolean isDisplayRowColHeadings() {
        return false;
    }

    @Override
    public void setRowBreak(int row) {

    }

    @Override
    public boolean isRowBroken(int row) {
        return false;
    }

    @Override
    public void removeRowBreak(int row) {

    }

    @Override
    public int[] getRowBreaks() {
        return new int[0];
    }

    @Override
    public int[] getColumnBreaks() {
        return new int[0];
    }

    @Override
    public void setColumnBreak(int column) {

    }

    @Override
    public boolean isColumnBroken(int column) {
        return false;
    }

    @Override
    public void removeColumnBreak(int column) {

    }

    @Override
    public void setColumnGroupCollapsed(int columnNumber, boolean collapsed) {

    }

    @Override
    public void groupColumn(int fromColumn, int toColumn) {

    }

    @Override
    public void ungroupColumn(int fromColumn, int toColumn) {

    }

    @Override
    public void groupRow(int fromRow, int toRow) {

    }

    @Override
    public void ungroupRow(int fromRow, int toRow) {

    }

    @Override
    public void setRowGroupCollapsed(int row, boolean collapse) {

    }

    @Override
    public void setDefaultColumnStyle(int column, CellStyle style) {

    }

    @Override
    public void autoSizeColumn(int column) {

    }

    @Override
    public void autoSizeColumn(int column, boolean useMergedCells) {

    }

    @Override
    public Comment getCellComment(CellAddress ref) {
        return null;
    }

    @Override
    public Map<CellAddress, ? extends Comment> getCellComments() {
        return null;
    }

    @Override
    public Drawing<?> getDrawingPatriarch() {
        return null;
    }

    @Override
    public Drawing<?> createDrawingPatriarch() {
        return null;
    }

    @Override
    public Workbook getWorkbook() {
        return csvWorkbook;
    }

    @Override
    public String getSheetName() {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public CellRange<? extends Cell> setArrayFormula(
        String formula, CellRangeAddress range) {
        return null;
    }

    @Override
    public CellRange<? extends Cell> removeArrayFormula(Cell cell) {
        return null;
    }

    @Override
    public DataValidationHelper getDataValidationHelper() {
        return null;
    }

    @Override
    public List<? extends DataValidation> getDataValidations() {
        return null;
    }

    @Override
    public void addValidationData(DataValidation dataValidation) {

    }

    @Override
    public AutoFilter setAutoFilter(CellRangeAddress range) {
        return null;
    }

    @Override
    public SheetConditionalFormatting getSheetConditionalFormatting() {
        return null;
    }

    @Override
    public CellRangeAddress getRepeatingRows() {
        return null;
    }

    @Override
    public CellRangeAddress getRepeatingColumns() {
        return null;
    }

    @Override
    public void setRepeatingRows(CellRangeAddress rowRangeRef) {

    }

    @Override
    public void setRepeatingColumns(CellRangeAddress columnRangeRef) {

    }

    @Override
    public int getColumnOutlineLevel(int columnIndex) {
        return 0;
    }

    @Override
    public Hyperlink getHyperlink(int row, int column) {
        return null;
    }

    @Override
    public Hyperlink getHyperlink(CellAddress addr) {
        return null;
    }

    @Override
    public List<? extends Hyperlink> getHyperlinkList() {
        return null;
    }

    @Override
    public CellAddress getActiveCell() {
        return null;
    }

    @Override
    public void setActiveCell(CellAddress address) {

    }

    @Override
    public Iterator<Row> iterator() {
        return rowIterator();
    }

    @Override
    public void close() throws IOException {
        // Avoid empty sheets
        initSheet();

        flushData();
        csvPrinter.flush();
        csvPrinter.close();
    }

    public void printData() {
        if (rowCache.size() >= rowCacheCount) {
            flushData();
        }
    }

    public void flushData() {
        try {
            for (CsvRow row : rowCache) {
                Iterator<Cell> cellIterator = row.cellIterator();
                int columnIndex = 0;
                while (cellIterator.hasNext()) {
                    CsvCell csvCell = (CsvCell)cellIterator.next();
                    while (csvCell.getColumnIndex() > columnIndex++) {
                        csvPrinter.print(null);
                    }
                    csvPrinter.print(buildCellValue(csvCell));
                }
                csvPrinter.println();
            }
            rowCache.clear();
        } catch (IOException e) {
            throw new ExcelGenerateException(e);
        }
    }

    private String buildCellValue(CsvCell csvCell) {
        switch (csvCell.getCellType()) {
            case STRING:
            case ERROR:
                return csvCell.getStringCellValue();
            case NUMERIC:
                Short dataFormat = null;
                String dataFormatString = null;
                if (csvCell.getCellStyle() != null) {
                    dataFormat = csvCell.getCellStyle().getDataFormat();
                    dataFormatString = csvCell.getCellStyle().getDataFormatString();
                }
                if (csvCell.getNumericCellType() == NumericCellTypeEnum.DATE) {
                    if (csvCell.getDateValue() == null) {
                        return null;
                    }
                    // date
                    if (dataFormat == null) {
                        dataFormatString = DateUtils.defaultDateFormat;
                        dataFormat = csvWorkbook.createDataFormat().getFormat(dataFormatString);
                    }
                    if (dataFormatString == null) {
                        dataFormatString = csvWorkbook.createDataFormat().getFormat(dataFormat);
                    }
                    return NumberDataFormatterUtils.format(BigDecimal.valueOf(
                            DateUtil.getExcelDate(csvCell.getDateValue(), csvWorkbook.getUse1904windowing())),
                        dataFormat, dataFormatString, csvWorkbook.getUse1904windowing(), csvWorkbook.getLocale(),
                        csvWorkbook.getUseScientificFormat());
                } else {
                    if (csvCell.getNumberValue() == null) {
                        return null;
                    }
                    //number
                    if (dataFormat == null) {
                        dataFormat = BuiltinFormats.GENERAL;
                        dataFormatString = csvWorkbook.createDataFormat().getFormat(dataFormat);
                    }
                    if (dataFormatString == null) {
                        dataFormatString = csvWorkbook.createDataFormat().getFormat(dataFormat);
                    }
                    return NumberDataFormatterUtils.format(csvCell.getNumberValue(), dataFormat, dataFormatString,
                        csvWorkbook.getUse1904windowing(), csvWorkbook.getLocale(),
                        csvWorkbook.getUseScientificFormat());
                }
            case BOOLEAN:
                return csvCell.getBooleanValue().toString();
            case BLANK:
                return StringUtils.EMPTY;
            default:
                return null;
        }
    }

}
