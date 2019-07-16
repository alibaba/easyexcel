package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.write.style.RowCellStyleStrategy;

/**
 * Load default handler
 * 
 * @author zhuangjiaju
 */
public class DefaultWriteHandlerLoader {

    /**
     * Load default handler
     *
     * @return
     */
    public static List<WriteHandler> loadDefaultHandler() {
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        CellStyle headCellStyle = new CellStyle();
        Font font = new Font();
        headCellStyle.setFont(font);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        headCellStyle.setIndexedColors(IndexedColors.GREY_25_PERCENT);
        handlerList.add(new RowCellStyleStrategy(headCellStyle, new ArrayList<CellStyle>()));
        return handlerList;
    }

}
