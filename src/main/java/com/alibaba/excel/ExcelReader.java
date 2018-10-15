package com.alibaba.excel;

import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.parameter.AnalysisParam;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.List;

/**
 * Excel thread unsafe
 *
 * @author jipengfei
 */
public class ExcelReader {

    /**
     * analyser
     */
    private ExcelAnalyser analyser = new ExcelAnalyserImpl();

    /**
     * @param in
     * @param excelTypeEnum 0307
     * @param customContent {@link AnalysisEventListener#invoke(Object, AnalysisContext)
     *                      }AnalysisContext
     * @param eventListener
     */
    @Deprecated
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
                       AnalysisEventListener eventListener) {
        this(in, excelTypeEnum, customContent, eventListener, true);
    }

    /**
     * @param in
     * @param customContent {@link AnalysisEventListener#invoke(Object, AnalysisContext)
     *                      }AnalysisContext
     * @param eventListener
     */
    public ExcelReader(InputStream in, Object customContent,
                       AnalysisEventListener eventListener) {
        this(in, customContent, eventListener, true);
    }

    /**
     * old 1.1.0
     * @param param
     * @param eventListener
     */
    @Deprecated
    public ExcelReader(AnalysisParam param, AnalysisEventListener eventListener) {
        this(param.getIn(), param.getExcelTypeEnum(), param.getCustomContent(), eventListener, true);
    }

    /**
     * @param in
     * @param excelTypeEnum 03 07
     * @param customContent {@link AnalysisEventListener#invoke(Object, AnalysisContext)
     *                      }AnalysisContext
     * @param eventListener
     * @param trim
     */
    @Deprecated
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
                       AnalysisEventListener eventListener, boolean trim) {
        validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, customContent, eventListener, trim);
    }

    /**
     * @param in
     * @param customContent {@link AnalysisEventListener#invoke(Object, AnalysisContext)
     *                      }AnalysisContext
     * @param eventListener
     * @param trim
     */
    public ExcelReader(InputStream in, Object customContent,
                       AnalysisEventListener eventListener, boolean trim) {
        ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.valueOf(in);
        validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, customContent, eventListener, trim);
    }

    /**
     */
    public void read() {
        analyser.analysis();
    }

    /**
     *
     * @param sheet
     */
    public void read(Sheet sheet) {
        analyser.analysis(sheet);
    }

    @Deprecated
    public void read(Sheet sheet,Class<? extends BaseRowModel> clazz){
        sheet.setClazz(clazz);
        analyser.analysis(sheet);
    }

    /**
     *
     * @return
     */
    public List<Sheet> getSheets() {
        return analyser.getSheets();
    }

    /**
     *
     * @param in
     * @param excelTypeEnum
     * @param eventListener
     */
    private void validateParam(InputStream in, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener) {
        if (eventListener == null) {
            throw new IllegalArgumentException("AnalysisEventListener can not null");
        } else if (in == null) {
            throw new IllegalArgumentException("InputStream can not null");
        } else if (excelTypeEnum == null) {
            throw new IllegalArgumentException("excelTypeEnum can not null");
        }
    }
}
