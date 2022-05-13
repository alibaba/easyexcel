package com.alibaba.excel.write.handler.chain;

import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Execute the workbook handler chain
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class WorkbookHandlerExecutionChain {
    /**
     * next chain
     */
    private WorkbookHandlerExecutionChain next;

    /**
     * handler
     */
    private WorkbookWriteHandler handler;

    public WorkbookHandlerExecutionChain(WorkbookWriteHandler handler) {
        this.handler = handler;
    }

    public void beforeWorkbookCreate(WorkbookWriteHandlerContext context) {
        this.handler.beforeWorkbookCreate(context);
        if (this.next != null) {
            this.next.beforeWorkbookCreate(context);
        }
    }

    public void afterWorkbookCreate(WorkbookWriteHandlerContext context) {
        this.handler.afterWorkbookCreate(context);
        if (this.next != null) {
            this.next.afterWorkbookCreate(context);
        }
    }

    public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        this.handler.afterWorkbookDispose(context);
        if (this.next != null) {
            this.next.afterWorkbookDispose(context);
        }
    }

    public void addLast(WorkbookWriteHandler handler) {
        WorkbookHandlerExecutionChain context = this;
        while (context.next != null) {
            context = context.next;
        }
        context.next = new WorkbookHandlerExecutionChain(handler);
    }
}
