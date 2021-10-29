package com.alibaba.excel.write.handler.chain;

import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Execute the cell handler chain
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CellHandlerExecutionChain {
    /**
     * next chain
     */
    private CellHandlerExecutionChain next;
    /**
     * handler
     */
    private CellWriteHandler handler;

    public CellHandlerExecutionChain(CellWriteHandler handler) {
        this.handler = handler;
    }

    public void beforeCellCreate(CellWriteHandlerContext context) {
        this.handler.beforeCellCreate(context);
        if (this.next != null) {
            this.next.beforeCellCreate(context);
        }
    }

    public void afterCellCreate(CellWriteHandlerContext context) {
        this.handler.afterCellCreate(context);
        if (this.next != null) {
            this.next.afterCellCreate(context);
        }
    }

    public void afterCellDataConverted(CellWriteHandlerContext context) {
        this.handler.afterCellDataConverted(context);
        if (this.next != null) {
            this.next.afterCellDataConverted(context);
        }
    }

    public void afterCellDispose(CellWriteHandlerContext context) {
        this.handler.afterCellDispose(context);
        if (this.next != null) {
            this.next.afterCellDispose(context);
        }
    }

    public void addLast(CellWriteHandler handler) {
        CellHandlerExecutionChain context = this;
        while (context.next != null) {
            context = context.next;
        }
        context.next = new CellHandlerExecutionChain(handler);
    }
}
