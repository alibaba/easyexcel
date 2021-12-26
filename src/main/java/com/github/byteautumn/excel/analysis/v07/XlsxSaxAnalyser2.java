package com.github.byteautumn.excel.analysis.v07;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.xml.sax.ContentHandler;

import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.analysis.v07.handlers.CellTagHandler;
import com.alibaba.excel.analysis.v07.handlers.XlsxTagHandler;
import com.alibaba.excel.analysis.v07.handlers.sax.XlsxRowHandler;
import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.SheetUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 重写到 07 版本的解析器入口
 * <p>
 * 本来：{@link XlsxSaxAnalyser}
 *
 * @author byte.autumn
 */
@Slf4j
public class XlsxSaxAnalyser2 extends XlsxSaxAnalyser {

    private final XlsxReadContext xlsxReadContext;

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
        try {
            List<ReadSheet> readSheets = this.sheetList();

            Class<?> xlsxSaxAnalyserClass = getClass().getSuperclass();

            Field field = xlsxSaxAnalyserClass.getDeclaredField("sheetMap");
            field.setAccessible(true);
            Map<Integer, InputStream> sheetMap = (Map<Integer, InputStream>) field.get(this);

            // 处理父方法中的方法
            Method parseXmlSourceMethod = xlsxSaxAnalyserClass.getDeclaredMethod("parseXmlSource", InputStream.class, ContentHandler.class);
            parseXmlSourceMethod.setAccessible(true);

            Method readCommentsMethod = xlsxSaxAnalyserClass.getDeclaredMethod("readComments", ReadSheet.class);
            readCommentsMethod.setAccessible(true);

            for (ReadSheet readSheet : readSheets) {
                readSheet = SheetUtils.match(readSheet, xlsxReadContext);
                if (readSheet != null) {
                    xlsxReadContext.currentSheet(readSheet);
                    parseXmlSourceMethod.invoke(this, sheetMap.get(readSheet.getSheetNo()), build());
                    // Read comments
                    readCommentsMethod.invoke(this, readSheet);
                    // The last sheet is read
                    xlsxReadContext.analysisEventProcessor().endSheet(xlsxReadContext);
                }
            }
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            log.error("获取 XlsxSaxAnalyser 中的 Field 或者 Method 失败！", e);
        } catch (IllegalAccessException e) {
            log.error("获取 Field 值失败！", e);
        } catch (InvocationTargetException e) {
            log.error("调用 父类私有方法 失败！", e);
        }
    }

    private XlsxRowHandler build() {
        XlsxRowHandler xlsxRowHandler = new XlsxRowHandler(xlsxReadContext);
        // 更改里面的解析器
        Class<?> rowClass = xlsxRowHandler.getClass();
        try {
            Field field = rowClass.getDeclaredField("XLSX_CELL_HANDLER_MAP");
            field.setAccessible(true);

            Map<String, XlsxTagHandler> XLSX_CELL_HANDLER_MAP = (Map<String, XlsxTagHandler>) field.get(xlsxRowHandler);

            CellTagHandler cellTagHandler = new CellTagHandler();
            XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.CELL_TAG, cellTagHandler);
            XLSX_CELL_HANDLER_MAP.put(ExcelXmlConstants.X_CELL_TAG, cellTagHandler);

        } catch (NoSuchFieldException e) {
            log.error("尝试获取 XLSX_CELL_HANDLER_MAP 失败！", e);
        }
        return xlsxRowHandler;
    }
}
