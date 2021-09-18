package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.write.handler.impl.DefaultRowWriteHandler;
import com.alibaba.excel.write.handler.impl.DimensionWorkbookWriteHandler;
import com.alibaba.excel.write.handler.impl.FillStyleCellWriteHandler;
import com.alibaba.excel.write.style.DefaultStyle;

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
            handlerList.add(new DefaultStyle());
        }
        handlerList.addAll(DEFAULT_WRITE_HANDLER_LIST);
        return handlerList;
    }

}
