package com.alibaba.excel.util;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.handler.chain.CellHandlerExecutionChain;
import com.alibaba.excel.write.handler.chain.RowHandlerExecutionChain;
import com.alibaba.excel.write.handler.chain.SheetHandlerExecutionChain;
import com.alibaba.excel.write.handler.chain.WorkbookHandlerExecutionChain;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.AbstractWriteHolder;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

/**
 * Write handler utils
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class WriteHandlerUtils {

    private WriteHandlerUtils() {}

    public static WorkbookWriteHandlerContext createWorkbookWriteHandlerContext(WriteContext writeContext) {
        WorkbookWriteHandlerContext context = new WorkbookWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder());
        writeContext.writeWorkbookHolder().setWorkbookWriteHandlerContext(context);
        return context;
    }

    public static void beforeWorkbookCreate(WorkbookWriteHandlerContext context) {
        beforeWorkbookCreate(context, false);
    }

    public static void beforeWorkbookCreate(WorkbookWriteHandlerContext context, boolean runOwn) {
        WorkbookHandlerExecutionChain workbookHandlerExecutionChain = getWorkbookHandlerExecutionChain(context, runOwn);
        if (workbookHandlerExecutionChain != null) {
            workbookHandlerExecutionChain.beforeWorkbookCreate(context);
        }
    }

    public static void afterWorkbookCreate(WorkbookWriteHandlerContext context) {
        afterWorkbookCreate(context, false);
    }

    public static void afterWorkbookCreate(WorkbookWriteHandlerContext context, boolean runOwn) {
        WorkbookHandlerExecutionChain workbookHandlerExecutionChain = getWorkbookHandlerExecutionChain(context, runOwn);
        if (workbookHandlerExecutionChain != null) {
            workbookHandlerExecutionChain.afterWorkbookCreate(context);
        }
    }

    private static WorkbookHandlerExecutionChain getWorkbookHandlerExecutionChain(WorkbookWriteHandlerContext context,
        boolean runOwn) {
        AbstractWriteHolder abstractWriteHolder = (AbstractWriteHolder)context.getWriteContext().currentWriteHolder();
        if (runOwn) {
            return abstractWriteHolder.getOwnWorkbookHandlerExecutionChain();
        } else {
            return abstractWriteHolder.getWorkbookHandlerExecutionChain();
        }
    }

    public static void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        WorkbookHandlerExecutionChain workbookHandlerExecutionChain = getWorkbookHandlerExecutionChain(context, false);
        if (workbookHandlerExecutionChain != null) {
            workbookHandlerExecutionChain.afterWorkbookDispose(context);
        }
    }

    public static SheetWriteHandlerContext createSheetWriteHandlerContext(WriteContext writeContext) {
        return new SheetWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder());
    }

    public static void beforeSheetCreate(SheetWriteHandlerContext context) {
        beforeSheetCreate(context, false);
    }

    public static void beforeSheetCreate(SheetWriteHandlerContext context, boolean runOwn) {
        SheetHandlerExecutionChain sheetHandlerExecutionChain = getSheetHandlerExecutionChain(context, runOwn);
        if (sheetHandlerExecutionChain != null) {
            sheetHandlerExecutionChain.beforeSheetCreate(context);
        }
    }

    public static void afterSheetCreate(SheetWriteHandlerContext context) {
        afterSheetCreate(context, false);
    }

    public static void afterSheetCreate(SheetWriteHandlerContext context, boolean runOwn) {
        SheetHandlerExecutionChain sheetHandlerExecutionChain = getSheetHandlerExecutionChain(context, runOwn);
        if (sheetHandlerExecutionChain != null) {
            sheetHandlerExecutionChain.afterSheetCreate(context);
        }
    }

    private static SheetHandlerExecutionChain getSheetHandlerExecutionChain(SheetWriteHandlerContext context,
        boolean runOwn) {
        AbstractWriteHolder abstractWriteHolder = (AbstractWriteHolder)context.getWriteContext().currentWriteHolder();
        if (runOwn) {
            return abstractWriteHolder.getOwnSheetHandlerExecutionChain();
        } else {
            return abstractWriteHolder.getSheetHandlerExecutionChain();
        }
    }

    public static CellWriteHandlerContext createCellWriteHandlerContext(WriteContext writeContext, Row row,
        Integer rowIndex, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead,
        ExcelContentProperty excelContentProperty) {
        return new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), row, rowIndex, null, columnIndex,
            relativeRowIndex, head, null, null, isHead, excelContentProperty);
    }

    public static void beforeCellCreate(CellWriteHandlerContext context) {
        CellHandlerExecutionChain cellHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getCellHandlerExecutionChain();
        if (cellHandlerExecutionChain != null) {
            cellHandlerExecutionChain.beforeCellCreate(context);
        }
    }

    public static void afterCellCreate(CellWriteHandlerContext context) {
        CellHandlerExecutionChain cellHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getCellHandlerExecutionChain();
        if (cellHandlerExecutionChain != null) {
            cellHandlerExecutionChain.afterCellCreate(context);
        }
    }

    public static void afterCellDataConverted(CellWriteHandlerContext context) {
        CellHandlerExecutionChain cellHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getCellHandlerExecutionChain();
        if (cellHandlerExecutionChain != null) {
            cellHandlerExecutionChain.afterCellDataConverted(context);
        }
    }

    public static void afterCellDispose(CellWriteHandlerContext context) {
        CellHandlerExecutionChain cellHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getCellHandlerExecutionChain();
        if (cellHandlerExecutionChain != null) {
            cellHandlerExecutionChain.afterCellDispose(context);
        }
    }

    public static RowWriteHandlerContext createRowWriteHandlerContext(WriteContext writeContext, Integer rowIndex,
        Integer relativeRowIndex, Boolean isHead) {
        return new RowWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), rowIndex, null, relativeRowIndex, isHead);
    }

    public static void beforeRowCreate(RowWriteHandlerContext context) {
        RowHandlerExecutionChain rowHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getRowHandlerExecutionChain();
        if (rowHandlerExecutionChain != null) {
            rowHandlerExecutionChain.beforeRowCreate(context);
        }
    }

    public static void afterRowCreate(RowWriteHandlerContext context) {
        RowHandlerExecutionChain rowHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getRowHandlerExecutionChain();
        if (rowHandlerExecutionChain != null) {
            rowHandlerExecutionChain.afterRowCreate(context);
        }
    }

    public static void afterRowDispose(RowWriteHandlerContext context) {
        RowHandlerExecutionChain rowHandlerExecutionChain = ((AbstractWriteHolder)context.getWriteContext()
            .currentWriteHolder()).getRowHandlerExecutionChain();
        if (rowHandlerExecutionChain != null) {
            rowHandlerExecutionChain.afterRowDispose(context);
        }
    }

}
