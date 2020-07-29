package com.alibaba.excel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;

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
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler) writeHandler).beforeWorkbookCreate();
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
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler) writeHandler).afterWorkbookCreate(writeContext.writeWorkbookHolder());
            }
        }
    }

    public static void afterWorkbookDispose(WriteContext writeContext) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(WorkbookWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof WorkbookWriteHandler) {
                ((WorkbookWriteHandler) writeHandler).afterWorkbookDispose(writeContext.writeWorkbookHolder());
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
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler) writeHandler).beforeSheetCreate(writeContext.writeWorkbookHolder(),
                    writeContext.writeSheetHolder());
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
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof SheetWriteHandler) {
                ((SheetWriteHandler) writeHandler).afterSheetCreate(writeContext.writeWorkbookHolder(),
                    writeContext.writeSheetHolder());
            }
        }
        if (null != writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()) {
            writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()
                .sheet(writeContext.writeSheetHolder().getSheetNo(), writeContext.writeSheetHolder().getSheet());
        }
    }

    public static void beforeCellCreate(WriteContext writeContext, Row row, Head head, Integer columnIndex,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler) writeHandler).beforeCellCreate(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), row, head, columnIndex, relativeRowIndex, isHead);
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
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler) writeHandler).afterCellCreate(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), cell, head, relativeRowIndex, isHead);
            }
        }
    }

    public static void afterCellDataConverted(WriteContext writeContext, CellData cellData, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler) writeHandler).afterCellDataConverted(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), cellData, cell, head, relativeRowIndex, isHead);
            }
        }
    }

    public static void afterCellDispose(WriteContext writeContext, CellData cellData, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<CellData> cellDataList = new ArrayList<CellData>();
        if (cell != null) {
            cellDataList.add(cellData);
        }
        afterCellDispose(writeContext, cellDataList, cell, head, relativeRowIndex, isHead);
    }

    public static void afterCellDispose(WriteContext writeContext, List<CellData> cellDataList, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList =
            writeContext.currentWriteHolder().writeHandlerMap().get(CellWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                ((CellWriteHandler) writeHandler).afterCellDispose(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), cellDataList, cell, head, relativeRowIndex, isHead);
            }
        }
        if (null != writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()) {
            writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler().cell(cell.getRowIndex(), cell);
        }
    }

    public static void beforeRowCreate(WriteContext writeContext, Integer rowIndex, Integer relativeRowIndex,
        Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler) writeHandler).beforeRowCreate(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), rowIndex, relativeRowIndex, isHead);
            }
        }
    }

    public static void afterRowCreate(WriteContext writeContext, Row row, Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler) writeHandler).afterRowCreate(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), row, relativeRowIndex, isHead);
            }
        }

    }

    public static void afterRowDispose(WriteContext writeContext, Row row, Integer relativeRowIndex, Boolean isHead) {
        List<WriteHandler> handlerList = writeContext.currentWriteHolder().writeHandlerMap().get(RowWriteHandler.class);
        if (handlerList == null || handlerList.isEmpty()) {
            return;
        }
        for (WriteHandler writeHandler : handlerList) {
            if (writeHandler instanceof RowWriteHandler) {
                ((RowWriteHandler) writeHandler).afterRowDispose(writeContext.writeSheetHolder(),
                    writeContext.writeTableHolder(), row, relativeRowIndex, isHead);
            }
        }
        if (null != writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler()) {
            writeContext.writeWorkbookHolder().getWriteWorkbook().getWriteHandler().row(row.getRowNum(), row);
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
