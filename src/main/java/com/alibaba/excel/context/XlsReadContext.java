package com.alibaba.excel.context;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 *
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public interface XlsReadContext extends AnalysisContext {

    RowTypeEnum tempRowType();

    void tempRowType(RowTypeEnum tempRowType);

    CellData cellData();

    void cellData(CellData cellData);

    CellExtra cellExtra();

    void cellExtra(CellExtra cellExtra);

    Map<Integer, Cell> cellMap();

    void cellMap(Map<Integer, Cell> cellMap);

    Integer rowIndex();

    void rowIndex(Integer rowIndex);

    Boolean ignoreRecord();

    void ignoreRecord(Boolean ignoreRecord);

    /**
     * Select the current table
     *
     * @param readSheet
     *            sheet to read
     */
    void currentSheet0(ReadSheet readSheet);

    FormatTrackingHSSFListener formatTrackingHSSFListener();

    HSSFWorkbook hsffWorkbook();

    List<BoundSheetRecord> boundSheetRecordList();

    void boundSheetRecordList(List<BoundSheetRecord> boundSheetRecordList);

    /**
     * Actual data
     *
     * @return
     */
    List<ReadSheet> readSheetDataList();

    /**
     * Actual data
     *
     * @return
     */
    void readSheetDataList(List<ReadSheet> readSheetDataList);

    Boolean needReadSheet();

    void needReadSheet(Boolean needReadSheet);

    Integer readSheetIndex();

    void readSheetIndex(Integer readSheetIndex);

    CellData tempCellData();

    void tempCellData(CellData tempCellData);

    Integer tempObjectIndex();

    void tempObjectIndex(Integer tempObjectIndex);

    Map<Integer, String> objectCacheMap();
}
