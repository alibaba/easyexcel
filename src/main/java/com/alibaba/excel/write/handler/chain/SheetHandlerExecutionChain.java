package com.alibaba.excel.write.handler.chain;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;

import lombok.Data;

/**
 * Execute the sheet handler chain
 *
 * @author Jiaju Zhuang
 */
@Data
public class SheetHandlerExecutionChain {
    /**
     * next chain
     */
    private SheetHandlerExecutionChain next;
    /**
     * handler
     */
    private SheetWriteHandler handler;

    public SheetHandlerExecutionChain(SheetWriteHandler handler) {
        this.handler = handler;
    }

    public void beforeSheetCreate(SheetWriteHandlerContext context) {
        this.handler.beforeSheetCreate(context);
        if (this.next != null) {
            this.next.beforeSheetCreate(context);
        }
    }

    public void afterSheetCreate(SheetWriteHandlerContext context) {
        this.handler.afterSheetCreate(context);
        if (this.next != null) {
            this.next.afterSheetCreate(context);
        }
    }
    public void addLast(SheetWriteHandler handler) {
        SheetHandlerExecutionChain context = this;
        while (context.next != null) {
            context = context.next;
        }
        context.next = new SheetHandlerExecutionChain(handler);
    }
}
