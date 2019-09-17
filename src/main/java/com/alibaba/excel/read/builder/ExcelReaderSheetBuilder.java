package com.alibaba.excel.read.builder;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * Build sheet
 *
 * @author Jiaju Zhuang
 */
public class ExcelReaderSheetBuilder {
    private ExcelReader excelReader;
    /**
     * Sheet
     */
    private ReadSheet readSheet;

    public ExcelReaderSheetBuilder() {
        this.readSheet = new ReadSheet();
    }

    public ExcelReaderSheetBuilder(ExcelReader excelReader) {
        this.readSheet = new ReadSheet();
        this.excelReader = excelReader;
    }

    /**
     * Starting from 0
     *
     * @param sheetNo
     * @return
     */
    public ExcelReaderSheetBuilder sheetNo(Integer sheetNo) {
        readSheet.setSheetNo(sheetNo);
        return this;
    }

    /**
     * sheet name
     *
     * @param sheetName
     * @return
     */
    public ExcelReaderSheetBuilder sheetName(String sheetName) {
        readSheet.setSheetName(sheetName);
        return this;
    }

    /**
     * Count the number of added heads when read sheet.
     *
     * <p>
     * 0 - This Sheet has no head ,since the first row are the data
     * <p>
     * 1 - This Sheet has one row head , this is the default
     * <p>
     * 2 - This Sheet has two row head ,since the third row is the data
     *
     * @param headRowNumber
     * @return
     */
    public ExcelReaderSheetBuilder headRowNumber(Integer headRowNumber) {
        readSheet.setHeadRowNumber(headRowNumber);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelReaderBuilder#head(List)} and {@link ExcelReaderBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelReaderSheetBuilder head(List<List<String>> head) {
        readSheet.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelReaderBuilder#head(List)} and {@link ExcelReaderBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelReaderSheetBuilder head(Class clazz) {
        readSheet.setClazz(clazz);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelReaderSheetBuilder registerConverter(Converter converter) {
        if (readSheet.getCustomConverterList() == null) {
            readSheet.setCustomConverterList(new ArrayList<Converter>());
        }
        readSheet.getCustomConverterList().add(converter);
        return this;
    }

    /**
     * Custom type listener run after default
     *
     * @param readListener
     * @return
     */
    public ExcelReaderSheetBuilder registerReadListener(ReadListener readListener) {
        if (readSheet.getCustomReadListenerList() == null) {
            readSheet.setCustomReadListenerList(new ArrayList<ReadListener>());
        }
        readSheet.getCustomReadListenerList().add(readListener);
        return this;
    }

    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     *
     * @param use1904windowing
     * @return
     */
    public ExcelReaderSheetBuilder use1904windowing(Boolean use1904windowing) {
        readSheet.setUse1904windowing(use1904windowing);
        return this;
    }

    /**
     * Automatic trim includes sheet name and content
     *
     * @param autoTrim
     * @return
     */
    public ExcelReaderSheetBuilder autoTrim(Boolean autoTrim) {
        readSheet.setAutoTrim(autoTrim);
        return this;
    }

    public ReadSheet build() {
        return readSheet;
    }

    /**
     * Sax read
     */
    public void doRead() {
        if (excelReader == null) {
            throw new ExcelGenerateException("Must use 'EasyExcelFactory.read().sheet()' to call this method");
        }
        excelReader.read(build());
        excelReader.finish();
    }

    /**
     * Synchronous reads return results
     *
     * @return
     */
    public List<Object> doReadSync() {
        if (excelReader == null) {
            throw new ExcelAnalysisException("Must use 'EasyExcelFactory.read().sheet()' to call this method");
        }
        SyncReadListener syncReadListener = new SyncReadListener();
        registerReadListener(syncReadListener);
        excelReader.read(build());
        excelReader.finish();
        return syncReadListener.getList();
    }

}
