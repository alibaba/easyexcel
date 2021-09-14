package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.write.handler.impl.DefaultRowWriteHandler;
import com.alibaba.excel.write.handler.impl.DimensionWorkbookWriteHandler;
import com.alibaba.excel.write.handler.impl.FillStyleCellWriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

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
        DEFAULT_WRITE_HANDLER_LIST.add(new FillStyleCellWriteHandler());
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
            headWriteCellStyle.setWrapped(true);
            headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            headWriteCellStyle.setLocked(true);
            headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headWriteCellStyle.setBorderTop(BorderStyle.THIN);
            headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            headWriteCellStyle.setBorderRight(BorderStyle.THIN);
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontName("宋体");
            headWriteFont.setFontHeightInPoints((short)14);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            handlerList.add(new HorizontalCellStyleStrategy(headWriteCellStyle, new ArrayList<>()));
        }
        handlerList.addAll(DEFAULT_WRITE_HANDLER_LIST);
        return handlerList;
    }

}
