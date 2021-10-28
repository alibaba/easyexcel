package com.alibaba.excel.util;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.handler.CellHandlerExecutionChain;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.handler.impl.DefaultRowWriteHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
        return createWorkbookWriteHandlerContext(writeContext, false);
    }

    public static WorkbookWriteHandlerContext createWorkbookWriteHandlerContext(WriteContext writeContext,
        boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, WorkbookWriteHandler.class, runOwn);
        WorkbookWriteHandlerContext context = new WorkbookWriteHandlerContext(writeContext, null, null, null);
        if (runOwn) {
            context.setOwnHandlerList(handlerList);
        } else {
            context.setHandlerList(handlerList);
        }
        writeContext.writeWorkbookHolder().setWorkbookWriteHandlerContext(context);
        return context;
    }

    public static void beforeWorkbookCreate(WorkbookWriteHandlerContext context) {
        beforeWorkbookCreate(context, false);
    }

    public static void beforeWorkbookCreate(WorkbookWriteHandlerContext context, boolean runOwn) {
        List<WriteHandler> handlerList;
        if (runOwn) {
            handlerList = context.getOwnHandlerList();
        } else {
            handlerList = context.getHandlerList();
        }
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((WorkbookWriteHandler)writeHandler).beforeWorkbookCreate(context);
            }
        }
    }

    public static void afterWorkbookCreate(WorkbookWriteHandlerContext context) {
        afterWorkbookCreate(context, false);
    }

    public static void afterWorkbookCreate(WorkbookWriteHandlerContext context, boolean runOwn) {
        List<WriteHandler> handlerList;
        if (runOwn) {
            handlerList = context.getOwnHandlerList();
        } else {
            handlerList = context.getHandlerList();
        }
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookCreate(context);
            }
        }
    }

    public static void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        List<WriteHandler> handlerList = context.getHandlerList();
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookDispose(context);
            }
        }
    }

    public static SheetWriteHandlerContext createSheetWriteHandlerContext(WriteContext writeContext) {
        return createSheetWriteHandlerContext(writeContext, false);
    }

    public static SheetWriteHandlerContext createSheetWriteHandlerContext(WriteContext writeContext, boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, SheetWriteHandler.class, runOwn);
        SheetWriteHandlerContext context = new SheetWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder(), writeContext.writeSheetHolder(), null, null);
        if (runOwn) {
            context.setOwnHandlerList(handlerList);
        } else {
            context.setHandlerList(handlerList);
        }
        return context;
    }

    public static void beforeSheetCreate(SheetWriteHandlerContext context) {
        beforeSheetCreate(context, false);
    }

    public static void beforeSheetCreate(SheetWriteHandlerContext context, boolean runOwn) {
        List<WriteHandler> handlerList;
        if (runOwn) {
            handlerList = context.getOwnHandlerList();
        } else {
            handlerList = context.getHandlerList();
        }
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((SheetWriteHandler)writeHandler).beforeSheetCreate(context);
            }
        }
    }

    public static void afterSheetCreate(SheetWriteHandlerContext context) {
        afterSheetCreate(context, false);
    }

    public static void afterSheetCreate(SheetWriteHandlerContext context, boolean runOwn) {
        List<WriteHandler> handlerList;
        if (runOwn) {
            handlerList = context.getOwnHandlerList();
        } else {
            handlerList = context.getHandlerList();
        }
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((SheetWriteHandler)writeHandler).afterSheetCreate(context);
            }
        }
    }

    public static CellWriteHandlerContext createCellWriteHandlerContext(WriteContext writeContext, Row row,
        Integer rowIndex, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead,
        ExcelContentProperty excelContentProperty) {
        CellHandlerExecutionChain heade = writeContext.currentWriteHolder().writeHandlerMap2().get(
            CellWriteHandler.class);
        CellWriteHandlerContext context = new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), row, rowIndex, null, columnIndex,
            relativeRowIndex, head, null, null, isHead, excelContentProperty,
            writeContext.currentWriteHolder().writeHandlerMap().get(
                CellWriteHandler.class));
        context.setCellHandlerExecutionChain(heade);
        return context;
    }

    public static void beforeCellCreate(CellWriteHandlerContext context) {

        context.getCellHandlerExecutionChain().beforeCellCreate(context);

    }

    public static void afterCellCreate(CellWriteHandlerContext context) {
        context.getCellHandlerExecutionChain().afterCellCreate(context);

    }

    public static void afterCellDataConverted(CellWriteHandlerContext context) {
        context.getCellHandlerExecutionChain().afterCellDataConverted(context);

    }

    public static void afterCellDispose(CellWriteHandlerContext context) {
        context.getCellHandlerExecutionChain().afterCellDispose(context);
    }

    //public static void beforeCellCreate(CellWriteHandlerContext context) {
    //
    //    List<WriteHandler> handlerList = context.getHandlerList();
    //    if (CollectionUtils.isNotEmpty(handlerList)) {
    //        CostUtil.count++;
    //
    //        for (WriteHandler writeHandler : handlerList) {
    //
    //            ((CellWriteHandler)writeHandler).beforeCellCreate(context);
    //        }
    //    }
    //}
    //
    //public static void afterCellCreate(CellWriteHandlerContext context) {
    //
    //    List<WriteHandler> handlerList = context.getHandlerList();
    //    if (CollectionUtils.isNotEmpty(handlerList)) {
    //        CostUtil.count++;
    //
    //        for (WriteHandler writeHandler : handlerList) {
    //
    //            ((CellWriteHandler)writeHandler).afterCellCreate(context);
    //        }
    //    }
    //
    //}
    //
    //public static void afterCellDataConverted(CellWriteHandlerContext context) {
    //
    //    List<WriteHandler> handlerList = context.getHandlerList();
    //    if (CollectionUtils.isNotEmpty(handlerList)) {
    //        CostUtil.count++;
    //        for (WriteHandler writeHandler : handlerList) {
    //            ((CellWriteHandler)writeHandler).afterCellDataConverted(context);
    //        }
    //    }
    //
    //}
    //
    //public static void afterCellDispose(CellWriteHandlerContext context) {
    //    List<WriteHandler> handlerList = context.getHandlerList();
    //    if (CollectionUtils.isNotEmpty(handlerList)) {
    //        CostUtil.count++;
    //        for (WriteHandler writeHandler : handlerList) {
    //            ((CellWriteHandler)writeHandler).afterCellDispose(context);
    //        }
    //    }
    //}

    //
    //public static void beforeCellCreate(CellWriteHandlerContext context) {
    //
    //    context.getCellHandlerExecutionChain().beforeCellCreate(context);
    //
    //    //List<WriteHandler> handlerList = context.getHandlerList();
    //    //DefaultStyle.defaultStyle.beforeCellCreate(context);
    //    //AbstractWriteHolder.abstractVerticalCellStyleStrategy.beforeCellCreate(context);
    //    //if (CollectionUtils.isNotEmpty(handlerList)) {
    //    //    CostUtil.count++;
    //    //
    //    //    for (WriteHandler writeHandler : handlerList) {
    //    //
    //    //        ((CellWriteHandler)writeHandler).beforeCellCreate(context);
    //    //    }
    //    //}
    //    //FillStyleCellWriteHandler.fillStyleCellWriteHandler.beforeCellCreate(context);
    //}
    //
    //public static void afterCellCreate(CellWriteHandlerContext context) {
    //    context.getCellHandlerExecutionChain().afterCellCreate(context);
    //
    //    //List<WriteHandler> handlerList = context.getHandlerList();
    //    //DefaultStyle.defaultStyle.afterCellCreate(context);
    //    //AbstractWriteHolder.abstractVerticalCellStyleStrategy.afterCellCreate(context);
    //    //if (CollectionUtils.isNotEmpty(handlerList)) {
    //    //    CostUtil.count++;
    //    //
    //    //    for (WriteHandler writeHandler : handlerList) {
    //    //
    //    //        ((CellWriteHandler)writeHandler).afterCellCreate(context);
    //    //    }
    //    //}
    //    //FillStyleCellWriteHandler.fillStyleCellWriteHandler.afterCellCreate(context);
    //
    //}
    //
    //public static void afterCellDataConverted(CellWriteHandlerContext context) {
    //    context.getCellHandlerExecutionChain().afterCellDataConverted(context);
    //
    //    //List<WriteHandler> handlerList = context.getHandlerList();
    //    ////DefaultStyle.defaultStyle.afterCellDataConverted(context);
    //    ////AbstractWriteHolder.abstractVerticalCellStyleStrategy.afterCellDataConverted(context);
    //    //if (CollectionUtils.isNotEmpty(handlerList)) {
    //    //    CostUtil.count++;
    //    //    for (WriteHandler writeHandler : handlerList) {
    //    //        ((CellWriteHandler)writeHandler).afterCellDataConverted(context);
    //    //    }
    //    //}
    //    //FillStyleCellWriteHandler.fillStyleCellWriteHandler.afterCellDataConverted(context);
    //
    //}
    //
    //public static void afterCellDispose(CellWriteHandlerContext context) {
    //    context.getCellHandlerExecutionChain().afterCellDispose(context);
    //    //List<WriteHandler> handlerList = context.getHandlerList();
    //    //DefaultStyle.defaultStyle.afterCellDispose(context);
    //    ////AbstractWriteHolder.abstractVerticalCellStyleStrategy.afterCellDispose(context);
    //    //if (CollectionUtils.isNotEmpty(handlerList)) {
    //    //    CostUtil.count++;
    //    //    for (WriteHandler writeHandler : handlerList) {
    //    //        ((CellWriteHandler)writeHandler).afterCellDispose(context);
    //    //    }
    //    //}
    //    //FillStyleCellWriteHandler.fillStyleCellWriteHandler.afterCellDispose(context);
    //}

    public static RowWriteHandlerContext createRowWriteHandlerContext(WriteContext writeContext, Integer rowIndex,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(
            RowWriteHandler.class);
        return new RowWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), rowIndex, null, relativeRowIndex,
            isHead, handlerList);
    }

    public static void beforeRowCreate(RowWriteHandlerContext context) {
        List<WriteHandler> handlerList = context.getHandlerList();
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(context);
            }
        }
    }

    public static void afterRowCreate(RowWriteHandlerContext context) {
        List<WriteHandler> handlerList = context.getHandlerList();
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((RowWriteHandler)writeHandler).afterRowCreate(context);
            }
        }
    }

    public static void afterRowDispose(RowWriteHandlerContext context) {
        List<WriteHandler> handlerList = context.getHandlerList();
        DefaultRowWriteHandler.defaultRowWriteHandler.afterRowDispose(context);
        if (CollectionUtils.isNotEmpty(handlerList)) {
            for (WriteHandler writeHandler : handlerList) {
                ((RowWriteHandler)writeHandler).afterRowDispose(context);
            }
        }
    }

    private static List<WriteHandler> getHandlerList(WriteContext writeContext, Class<? extends
        WriteHandler> clazz,
        boolean runOwn) {
        Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap;
        if (runOwn) {
            writeHandlerMap = writeContext.currentWriteHolder().ownWriteHandlerMap();
        } else {
            writeHandlerMap = writeContext.currentWriteHolder().writeHandlerMap();
        }
        return writeHandlerMap.get(clazz);
    }
}
