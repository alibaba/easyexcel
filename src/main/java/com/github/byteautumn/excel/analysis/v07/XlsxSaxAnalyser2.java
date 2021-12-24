package com.github.byteautumn.excel.analysis.v07;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.analysis.v07.handlers.sax.XlsxRowHandler;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.SheetUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 重写到 07 版本的解析器入口
 *
 * 本来：{@link XlsxSaxAnalyser}
 * @author byte.autumn
 */
@Slf4j
public class XlsxSaxAnalyser2 extends XlsxSaxAnalyser {

    private XlsxReadContext xlsxReadContext;

    public XlsxSaxAnalyser2(XlsxReadContext xlsxReadContext, InputStream decryptedStream) throws Exception {
        super(xlsxReadContext, decryptedStream);
        this.xlsxReadContext = xlsxReadContext;
    }

    /**
     * 重写最终入口
     */
    @Override
    public void execute() {

        // 不能直接访问到 父类 中的 sheetList / parseXmlSource / readComments / xlsxReadContext 等
        // 只能通过 反射 的方式开始了

        Class<?> xlsxSaxAnalyserClass = super.getClass();
        this.sheetList();
        Field field = null;
        try {
            field = xlsxSaxAnalyserClass.getField("sheetMap");
            field.setAccessible(true);
            Map<Integer, InputStream> sheetMap = (Map<Integer, InputStream>) field.get(this);
        } catch (NoSuchFieldException e) {
            log.error("获取 XlsxSaxAnalyser 中的 sheetList 失败！", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        /*for (ReadSheet readSheet : this.sheetList()) {
            readSheet = SheetUtils.match(readSheet, xlsxReadContext);
            if (readSheet != null) {
                xlsxReadContext.currentSheet(readSheet);
                parseXmlSource(sheetMap.get(readSheet.getSheetNo()), new XlsxRowHandler(xlsxReadContext));
                // Read comments
                readComments(readSheet);
                // The last sheet is read
                xlsxReadContext.analysisEventProcessor().endSheet(xlsxReadContext);
            }
        }*/
    }
}
