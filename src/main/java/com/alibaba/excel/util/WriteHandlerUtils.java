package com.alibaba.excel.util;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Write handler utils
 *
 * @author Jiaju Zhuang
 */
public class WriteHandlerUtils {

    private WriteHandlerUtils() {}

    public static void beforeWorkbookCreate(WriteContext writeContext) {
        beforeWorkbookCreate(writeContext, false);
    }

    public static void beforeWorkbookCreate(WriteContext writeContext, boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, WorkbookWriteHandler.class, runOwn);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        WorkbookWriteHandlerContext context = new WorkbookWriteHandlerContext(writeContext, null);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).beforeWorkbookCreate(context);
            }
        }
    }

    public static void afterWorkbookCreate(WriteContext writeContext) {
        afterWorkbookCreate(writeContext, false);
    }

    public static void afterWorkbookCreate(WriteContext writeContext, boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, WorkbookWriteHandler.class, runOwn);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        WorkbookWriteHandlerContext context = new WorkbookWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder());
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookCreate(context);
            }
        }
    }

    public static void afterWorkbookDispose(WriteContext writeContext) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(WorkbookWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        WorkbookWriteHandlerContext context = new WorkbookWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder());
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler)writeHandler).afterWorkbookDispose(context);
            }
        }
    }

    public static void beforeSheetCreate(WriteContext writeContext) {
        beforeSheetCreate(writeContext, false);
    }

    public static void beforeSheetCreate(WriteContext writeContext, boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, SheetWriteHandler.class, runOwn);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        SheetWriteHandlerContext context = new SheetWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder(), writeContext.writeSheetHolder());
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).beforeSheetCreate(context);
            }
        }
    }

    public static void afterSheetCreate(WriteContext writeContext) {
        afterSheetCreate(writeContext, false);
    }

    public static void afterSheetCreate(WriteContext writeContext, boolean runOwn) {
        List<WriteHandler> handlerList = getHandlerList(writeContext, SheetWriteHandler.class, runOwn);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        SheetWriteHandlerContext context = new SheetWriteHandlerContext(writeContext,
            writeContext.writeWorkbookHolder(), writeContext.writeSheetHolder());
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler)writeHandler).afterSheetCreate(context);
            }
        }
    }

    public static void beforeCellCreate(WriteContext writeContext, Row row, Head head, Integer columnIndex,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        CellWriteHandlerContext context = new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), row, null, columnIndex, relativeRowIndex,
            head, null, null, isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).beforeCellCreate(context);
            }
        }
    }

    public static void afterCellCreate(WriteContext writeContext, Cell cell, Head head, Integer relativeRowIndex,
        Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        CellWriteHandlerContext context = new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), cell.getRow(), cell,
            cell.getColumnIndex(), relativeRowIndex, head, null, null, isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellCreate(context);
            }
        }
    }

    public static void afterCellDataConverted(WriteContext writeContext, WriteCellData<?> cellData, Cell cell,
        Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        List<WriteCellData<?>> cellDataList = cellData == null ? null : ListUtils.newArrayList(cellData);
        CellWriteHandlerContext context = new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), cell.getRow(), cell,
            cell.getColumnIndex(), relativeRowIndex, head, cellDataList, cellData, isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellDataConverted(context);
            }
        }
    }

    public static void afterCellDispose(WriteContext writeContext, WriteCellData<?> cellData, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteCellData<?>> cellDataList = cellData == null ? null : ListUtils.newArrayList(cellData);
        afterCellDispose(writeContext, cellDataList, cell, head, relativeRowIndex, isHead);
    }

    public static void afterCellDispose(WriteContext writeContext, List<WriteCellData<?>> cellDataList, Cell cell,
        Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        WriteCellData<?> cellData = null;
        if (CollectionUtils.isNotEmpty(cellDataList)) {
            cellData = cellDataList.get(0);
        }
        CellWriteHandlerContext context = new CellWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), cell.getRow(), cell,
            cell.getColumnIndex(), relativeRowIndex, head, cellDataList, cellData, isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler)writeHandler).afterCellDispose(context);
            }
        }
    }

    public static void beforeRowCreate(WriteContext writeContext, Integer rowIndex, Integer relativeRowIndex,
        Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        RowWriteHandlerContext context = new RowWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), rowIndex, null, relativeRowIndex, isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).beforeRowCreate(context);
            }
        }
    }

    public static void afterRowCreate(WriteContext writeContext, Row row, Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        RowWriteHandlerContext context = new RowWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), row.getRowNum(), row, relativeRowIndex,
            isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowCreate(context);
            }
        }
    }

    public static void afterRowDispose(WriteContext writeContext, Row row, Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        RowWriteHandlerContext context = new RowWriteHandlerContext(writeContext, writeContext.writeWorkbookHolder(),
            writeContext.writeSheetHolder(), writeContext.writeTableHolder(), row.getRowNum(), row, relativeRowIndex,
            isHead);
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler)writeHandler).afterRowDispose(context);
            }
        }
    }

    private static List<WriteHandler> getHandlerList(WriteContext writeContext, Class<? extends WriteHandler> clazz,
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
