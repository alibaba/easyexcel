package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

/**
 * Load default handler
 *
 * @author Jiaju Zhuang
 */
public class DefaultWriteHandlerLoader {

    /**
     * Load default handler
     *
     * @return
     */
    public static List<WriteHandler> loadDefaultHandler(Boolean useDefaultStyle) {
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (useDefaultStyle) {
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontName("宋体");
            headWriteFont.setFontHeightInPoints((short)14);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            handlerList.add(new HorizontalCellStyleStrategy(headWriteCellStyle, new ArrayList<WriteCellStyle>()));
        }
        return handlerList;
    }

}
