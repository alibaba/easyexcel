package com.alibaba.excel.context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.BoundSheetRecord;

import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;

/**
 *
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public class DefaultXlsReadContext extends AnalysisContextImpl implements XlsReadContext {
    private Map<Integer, Cell> cellMap;
    private RowTypeEnum rowType;
    private Integer rowIndex;
    private CellData cellData;
    private CellExtra cellExtra;
    /**
     * Excel 2003 cannot read specific sheet. It can only read sheet by sheet.So when you specify one sheet, you ignore
     * the others.
     */
    private Boolean ignoreRecord;

    /**
     * Bound sheet record
     *
     * @return
     */
    List<BoundSheetRecord> boundSheetRecordList;

    public DefaultXlsReadContext(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        cellMap = new LinkedHashMap<Integer, Cell>();
        rowType = RowTypeEnum.EMPTY;
        boundSheetRecordList = new ArrayList<BoundSheetRecord>();
    }

    @Override
    public RowTypeEnum rowType() {
        return null;
    }

    @Override
    public void rowType(RowTypeEnum rowType) {

    }

    @Override
    public RowTypeEnum tempRowType() {
        return null;
    }

    @Override
    public void tempRowType(RowTypeEnum tempRowType) {

    }

    @Override
    public CellData cellData() {
        return null;
    }

    @Override
    public void cellData(CellData cellData) {

    }

    @Override
    public CellExtra cellExtra() {
        return null;
    }

    @Override
    public void cellExtra(CellExtra cellExtra) {

    }

    @Override
    public Map<Integer, Cell> cellMap() {
        return null;
    }

    @Override
    public void cellMap(Map<Integer, Cell> cellMap) {

    }

    @Override
    public Integer rowIndex() {
        return null;
    }

    @Override
    public void rowIndex(Integer rowIndex) {

    }

    @Override
    public Boolean ignoreRecord() {
        return null;
    }

    @Override
    public void ignoreRecord(Boolean ignoreRecord) {

    }

    @Override
    public void currentSheet0(ReadSheet readSheet) {
        currentSheet(readSheet);
        cellMap = new LinkedHashMap<Integer, Cell>();
        ignoreRecord = Boolean.FALSE;
        rowType = RowTypeEnum.EMPTY;
    }
}
