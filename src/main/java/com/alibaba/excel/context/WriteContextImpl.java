package com.alibaba.excel.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.style.CellStyleStrategy;

/**
 * A context is the main anchorage point of a excel writer.
 *
 * @author jipengfei
 */
public class WriteContextImpl implements WriteContext {
    /**
     * prevent duplicate creation of sheet objects
     */
    private final Map<Integer, SheetHolder> hasBeenInitializedSheet = new HashMap<Integer, SheetHolder>();
    /**
     * Current sheet holder
     */
    private SheetHolder currentSheetHolder;
    /**
     * The table currently written
     */
    private TableHolder currentTableHolder;
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;

    /**
     * POI Workbook
     */
    private Workbook workbook;

    /**
     * Final output stream
     */
    private OutputStream outputStream;

    /**
     * If sheet and table don't have {@link CellStyleStrategy} , use this one. If they have, use their own
     */
    private CellStyleStrategy defalutCellStyleStrategy;

    private WriteHandler writeHandler;

    private ConverterRegistryCenter registryCenter;

    public WriteContextImpl(InputStream templateInputStream, OutputStream out, ExcelTypeEnum excelType,
        boolean needHead, WriteHandler writeHandler, ConverterRegistryCenter registryCenter) throws IOException {
        this.needHead = needHead;
        this.outputStream = out;
        this.writeHandler = writeHandler;
        this.workbook = WorkBookUtil.createWorkBook(templateInputStream, excelType);
        this.defaultCellStyle = StyleUtil.buildDefaultCellStyle(this.workbook);
        this.registryCenter = registryCenter;
    }

    /**
     * @param sheet
     */
    @Override
    public void currentSheet(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        if (hasBeenInitializedSheet.containsKey(sheet.getSheetNo())) {
            currentSheetHolder = hasBeenInitializedSheet.get(sheet.getSheetNo());
            return;
        }
        // create sheet
        currentSheetParam = sheet;
        try {
            this.currentSheet = workbook.getSheetAt(sheet.getSheetNo() - 1);
        } catch (Exception e) {
            this.currentSheet = WorkBookUtil.createSheet(workbook, sheet);
            if (null != writeHandler) {
                this.writeHandler.sheet(sheet.getSheetNo(), currentSheet);
            }
        }
        // Initialization current sheet
        initCurrentSheet();
    }

    private void initCurrentSheet() {
        // Initialization head
        initExcelHeadProperty();
        // Initialization cell style strategy
        initCellStyleStrategy();
        // Initialization sheet head
        initSheetHead();
    }

    private void initCellStyleStrategy() {
        if (currentSheetParam.getCellStyleStrategy() != null) {
            currentSheetHolder.setCellStyleStrategy(currentSheetParam.getCellStyleStrategy());
        }
        currentSheetHolder.setCellStyleStrategy(defalutCellStyleStrategy);
    }

    private void initTableCellStyleStrategy() {
        if (.getCellStyleStrategy() != null) {
            currentSheetHolder.setCellStyleStrategy(currentSheetParam.getCellStyleStrategy());
        }
        currentSheetHolder.setCellStyleStrategy(defalutCellStyleStrategy);
    }

    /**
     * init excel header
     */
    private void initExcelHeadProperty() {
        currentSheetHolder
            .setExcelHeadProperty(new ExcelHeadProperty(currentSheetParam.getClazz(), currentSheetParam.getHead()));
    }

    /**
     * init table excel header
     */
    private void initTableExcelHeadProperty() {
        currentTableHolder.setExcelHeadProperty(
            new ExcelHeadProperty(getCurrentTableParam().getClazz(), getCurrentTableParam().getHead()));
    }

    /**
     * init sheet column with
     */
    public void initSheetColumnWidth() {
        for (Head head : currentSheetHolder.getExcelHeadProperty().getHeadList()) {
            currentSheet.setColumnWidth(head.getColumnIndex(), currentSheetHolder.getColumnWidthStyleStrategy()
                .columnWidth(head.getColumnIndex(), head.getFieldName()));
        }
    }

    public void initSheetHead() {
        if (!currentSheetHolder.isNeedHead() || !currentSheetHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int startRow = getCurrentSheet().getLastRowNum();
        if (startRow > 0) {
            startRow += 4;
        } else {
            startRow = getCurrentSheetParam().getStartRow();
        }
        // Combined head
        addMergedRegionToCurrentSheet(startRow);
        for (int i = startRow; i < currentSheetHolder.getExcelHeadProperty().getHeadRowNumber() + startRow; i++) {
            Row row = WorkBookUtil.createRow(getCurrentSheet(), i);
            // Set the row height of the header
            currentSheetHolder.getRowHighStyleStrategy().headColumnHigh(i);
            if (null != writeHandler) {
                this.writeHandler.row(i, row);
            }
            addOneRowOfHeadDataToExcel(row, i, currentSheetHolder.getExcelHeadProperty().getHeadList());
        }
        // Initialization sheet column width
        initSheetColumnWidth();
    }

    public void initTableHead() {
        if (!currentTableHolder.isNeedHead() || !currentTableHolder.getExcelHeadProperty().hasHead()) {
            return;
        }
        int startRow = getCurrentSheet().getLastRowNum();
        if (startRow > 0) {
            startRow += 4;
        } else {
            startRow = getCurrentSheetParam().getStartRow();
        }
        // Combined head
        addMergedRegionToCurrentSheet(startRow);
        for (int i = startRow; i < currentTableHolder.getExcelHeadProperty().getHeadRowNumber() + startRow; i++) {
            Row row = WorkBookUtil.createRow(getCurrentSheet(), i);
            // Set the row height of the header
            currentTableHolder.getRowHighStyleStrategy().headColumnHigh(i);
            if (null != writeHandler) {
                this.writeHandler.row(i, row);
            }
            addOneRowOfHeadDataToExcel(row, i, currentTableHolder.getExcelHeadProperty().getHeadList());
        }
        // Initialization sheet column width
        initSheetColumnWidth();
    }

    private void addMergedRegionToCurrentSheet(int startRow) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : currentSheetHolder.getExcelHeadProperty()
            .getCellRangeModels()) {
            currentSheet.addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + startRow,
                cellRangeModel.getLastRow() + startRow, cellRangeModel.getFirstCol(), cellRangeModel.getLastCol()));
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, int rowIndex, List<Head> headList) {
        for (int i = 0; i < headList.size(); i++) {
            Head head = headList.get(i);
            Cell cell = WorkBookUtil.createCell(row, i,
                currentSheetHolder.getCellStyleStrategy().headCellStyle(rowIndex, headList.get(i)),
                head.getHeadName(i));
            if (null != writeHandler) {
                this.writeHandler.cell(i, cell);
            }
        }
    }

    private void cleanCurrentTable() {
        this.excelHeadProperty = null;
        this.currentHeadCellStyle = null;
        this.currentContentCellStyle = null;
        this.currentTable = null;

    }

    @Override
    public void currentTable(Table table) {
        if (table == null) {
            return;
        }
        if (currentSheetHolder == null) {
            throw new IllegalStateException("Must first call WriteContextImpl#currentSheet");
        }
        if(currentSheetHolder.getHasBeenInitializedTable().containsKey(table.getTableNo())){
            currentTableHolder=currentSheetHolder.getHasBeenInitializedTable().get(table.getTableNo());
            return;
        }


        
        
        currentTable.getTableNo()


        if (null == currentTable || currentTable.getTableNo() != table.getTableNo()) {
            cleanCurrentTable();
            this.currentTable = table;
            this.initExcelHeadProperty(table.getHead(), table.getClazz());
            this.initTableStyle(table.getTableStyle());
            this.initTableHead();
        }

    }

    @Override
    public ExcelHeadProperty getExcelHeadProperty() {
        return currentSheetHolder.getExcelHeadProperty();
    }

    @Override
    public boolean needHead() {
        return this.needHead;
    }

    @Override
    public Sheet getCurrentSheet() {
        return currentSheetHolder.getSheet();
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public String getCurrentSheetName() {
        return currentSheetHolder.getCurrentSheetParam();
    }

    public void setCurrentSheetName(String currentSheetName) {
        this.currentSheetName = currentSheetName;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public CellStyle getCurrentHeadCellStyle() {
        return this.currentHeadCellStyle == null ? defaultCellStyle : this.currentHeadCellStyle;
    }

    public CellStyle getCurrentContentStyle() {
        return this.currentContentCellStyle;
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    public com.alibaba.excel.metadata.Sheet getCurrentSheetParam() {
        return currentSheetHolder.getCurrentSheetParam();
    }

    public void setCurrentSheetParam(com.alibaba.excel.metadata.Sheet currentSheetParam) {
        this.currentSheetParam = currentSheetParam;
    }

    public Table getCurrentTableParam() {
        return currentTableHolder.getCurrentTableParam();
    }

    public Table getCurrentTable() {
        return currentTable;
    }

    @Override
    public ConverterRegistryCenter getConverterRegistryCenter() {
        return registryCenter;
    }
}
