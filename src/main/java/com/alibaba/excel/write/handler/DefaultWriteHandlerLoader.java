package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.write.handler.impl.DefaultRowWriteHandler;
import com.alibaba.excel.write.handler.impl.DimensionWorkbookWriteHandler;
import com.alibaba.excel.write.handler.impl.FillDataFormatCellWriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Load default handler
 *
 * @author Jiaju Zhuang
 */
public class DefaultWriteHandlerLoader {

    public static final List<WriteHandler> DEFAULT_WRITE_HANDLER_LIST = new ArrayList<>();

    static {
        DEFAULT_WRITE_HANDLER_LIST.add(new DimensionWorkbookWriteHandler());
        DEFAULT_WRITE_HANDLER_LIST.add(new DefaultRowWriteHandler());
    }

    /**
     * Load default handler
     *
     * @return
     */
    public static List<WriteHandler> loadDefaultHandler(Boolean useDefaultStyle) {
        List<WriteHandler> handlerList = new ArrayList<>();
        if (useDefaultStyle) {
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontName("宋体");
            headWriteFont.setFontHeightInPoints((short)14);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            handlerList.add(new HorizontalCellStyleStrategy(headWriteCellStyle, new ArrayList<>()));
        }
        handlerList.addAll(DEFAULT_WRITE_HANDLER_LIST);
        handlerList.add(new FillDataFormatCellWriteHandler());
        return handlerList;
    }

}
