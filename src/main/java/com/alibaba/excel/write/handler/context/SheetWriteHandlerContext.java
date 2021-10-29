package com.alibaba.excel.write.handler.context;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * sheet context
 *
 * @author Jiaju Zhuang
 */
@Data
@AllArgsConstructor
public class SheetWriteHandlerContext {
    /**
     * write context
     */
    private WriteContext writeContext;
    /**
     * workbook
     */
    private WriteWorkbookHolder writeWorkbookHolder;
    /**
     * sheet
     */
    private WriteSheetHolder writeSheetHolder;

    /**
     * handler
     */
    private List<WriteHandler> handlerList;
    /**
     * handler
     */
    private List<WriteHandler> ownHandlerList;
}
