package com.alibaba.excel.read.metadata.holder.xls;

import java.util.List;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
public class XlsReadWorkbookHolder extends ReadWorkbookHolder {
    /**
     * File System
     */
    private POIFSFileSystem poifsFileSystem;
    /**
     * Format tracking HSSFListener
     */
    private FormatTrackingHSSFListener formatTrackingHSSFListener;
    /**
     * HSSFWorkbook
     */
    private HSSFWorkbook hsffWorkbook;
    /**
     * Bound sheet record list.
     */
    private List<BoundSheetRecord> boundSheetRecordList;
    /**
     * Need read sheet.
     */
    private Boolean needReadSheet;
    /**
     * Sheet Index
     */
    private Integer readSheetIndex;

    public XlsReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook);
    }

    public POIFSFileSystem getPoifsFileSystem() {
        return poifsFileSystem;
    }

    public void setPoifsFileSystem(POIFSFileSystem poifsFileSystem) {
        this.poifsFileSystem = poifsFileSystem;
    }

    public FormatTrackingHSSFListener getFormatTrackingHSSFListener() {
        return formatTrackingHSSFListener;
    }

    public void setFormatTrackingHSSFListener(FormatTrackingHSSFListener formatTrackingHSSFListener) {
        this.formatTrackingHSSFListener = formatTrackingHSSFListener;
    }

    public HSSFWorkbook getHsffWorkbook() {
        return hsffWorkbook;
    }

    public void setHsffWorkbook(HSSFWorkbook hsffWorkbook) {
        this.hsffWorkbook = hsffWorkbook;
    }

    public List<BoundSheetRecord> getBoundSheetRecordList() {
        return boundSheetRecordList;
    }

    public void setBoundSheetRecordList(List<BoundSheetRecord> boundSheetRecordList) {
        this.boundSheetRecordList = boundSheetRecordList;
    }

    public Boolean getNeedReadSheet() {
        return needReadSheet;
    }

    public void setNeedReadSheet(Boolean needReadSheet) {
        this.needReadSheet = needReadSheet;
    }

    public Integer getReadSheetIndex() {
        return readSheetIndex;
    }

    public void setReadSheetIndex(Integer readSheetIndex) {
        this.readSheetIndex = readSheetIndex;
    }
}
