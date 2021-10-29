package com.alibaba.excel.write.handler.chain;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Execute the row handler chain
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class RowHandlerExecutionChain {
    /**
     * next chain
     */
    private RowHandlerExecutionChain next;
    /**
     * handler
     */
    private RowWriteHandler handler;

    public RowHandlerExecutionChain(RowWriteHandler handler) {
        this.handler = handler;
    }

    public void beforeRowCreate(RowWriteHandlerContext context) {
        this.handler.beforeRowCreate(context);
        if (this.next != null) {
            this.next.beforeRowCreate(context);
        }
    }

    public void afterRowCreate(RowWriteHandlerContext context) {
        this.handler.afterRowCreate(context);
        if (this.next != null) {
            this.next.afterRowCreate(context);
        }
    }

    public void afterRowDispose(RowWriteHandlerContext context) {
        this.handler.afterRowDispose(context);
        if (this.next != null) {
            this.next.afterRowDispose(context);
        }
    }

    public void addLast(RowWriteHandler handler) {
        RowHandlerExecutionChain context = this;
        while (context.next != null) {
            context = context.next;
        }
        context.next = new RowHandlerExecutionChain(handler);
    }
}
