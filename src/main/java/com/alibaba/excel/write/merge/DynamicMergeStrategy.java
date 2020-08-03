package com.alibaba.excel.write.merge;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * The regions of the dynamic merge by realtime compute
 *
 * @author ZhiPeng Xu
 */
public class DynamicMergeStrategy implements RowWriteHandler, WorkbookWriteHandler {

    /**
     * Each one just use once at row dispose and sheet dispose
     */
    private final Map<Integer, List<SheetRegions>> sheetRegions = new HashMap<Integer, List<SheetRegions>>();

    private Stack<CellRangeAddress> getRegions(final WriteSheet sheet) {

        final int key = SheetRegions.hashCode(sheet);

        final List<SheetRegions> sheets = this.sheetRegions.get(key);
        if (sheets != null) {
            for (final SheetRegions sheetRegions : sheets) {
                if (sheet == sheetRegions.sheet) {
                    return sheetRegions.regions;
                }
            }
        }

        return null;
    }

    private Stack<CellRangeAddress> getOrCreateRegions(final WriteSheet sheet) {

        final int key = SheetRegions.hashCode(sheet);

        List<SheetRegions> sheets = sheetRegions.get(key);
        if (sheets != null) {
            for (final SheetRegions sheetRegions : sheets) {
                if (sheet == sheetRegions.sheet) {
                    return sheetRegions.regions;
                }
            }
        } else {
            sheets = new ArrayList<SheetRegions>();
            this.sheetRegions.put(key, sheets);
        }

        final SheetRegions sheetRegions = new SheetRegions(sheet);
        sheets.add(sheetRegions);
        return sheetRegions.regions;
    }

    /**
     * append a region for merge
     *
     * @param region
     * @param sheet
     */
    public void append(final CellRangeAddress region, WriteSheet sheet) {
        final Stack<CellRangeAddress> regions = getOrCreateRegions(sheet);
        regions.push(region);
    }

    /**
     * merge
     *
     * @param holder
     */
    protected void merge(final WriteSheetHolder holder) {
        final Stack<CellRangeAddress> regions = getRegions(holder.getWriteSheet());
        if (regions != null) {
            final Sheet sheet = holder.getSheet();
            while (!regions.empty()) {
                sheet.addMergedRegion(
                    regions.pop()
                );
            }
        }
    }

    // region do nothing...

    @Override
    public void beforeWorkbookCreate() {

        // do nothing...
    }

    @Override
    public void afterWorkbookCreate(
        final WriteWorkbookHolder writeWorkbookHolder) {

        // do nothing...
    }

    @Override
    public void beforeRowCreate(
        final WriteSheetHolder writeSheetHolder,
        final WriteTableHolder writeTableHolder,
        final Integer rowIndex,
        final Integer relativeRowIndex,
        final Boolean isHead) {

        // do nothing...
    }

    @Override
    public void afterRowCreate(
        final WriteSheetHolder writeSheetHolder,
        final WriteTableHolder writeTableHolder,
        final Row row,
        final Integer relativeRowIndex,
        final Boolean isHead) {

        // do nothing...
    }

    // endregion do nothing...

    @Override
    public void afterRowDispose(
        final WriteSheetHolder writeSheetHolder,
        final WriteTableHolder writeTableHolder,
        final Row row,
        final Integer relativeRowIndex,
        final Boolean isHead) {

        if (!isHead) {
            merge(writeSheetHolder);
        }
    }

    @Override
    public void afterWorkbookDispose(
        final WriteWorkbookHolder writeWorkbookHolder) {

        final Map<Integer, WriteSheetHolder> indexes = writeWorkbookHolder.getHasBeenInitializedSheetIndexMap();
        for (final Integer index : indexes.keySet()) {
            final WriteSheetHolder writeSheetHolder = indexes.get(index);
            merge(writeSheetHolder);
        }

        final Map<String, WriteSheetHolder> names = writeWorkbookHolder.getHasBeenInitializedSheetNameMap();
        for (final String name : names.keySet()) {
            final WriteSheetHolder writeSheetHolder = names.get(name);
            merge(writeSheetHolder);
        }

        sheetRegions.clear();
    }

    private static class SheetRegions {

        public static int hashCode(final WriteSheet sheet) {
            int sheetNoHashCode = 0;
            int sheetNameHashCode = 0;
            if (sheet != null) {
                if (sheet.getSheetNo() != null) {
                    sheetNoHashCode = sheet.getSheetNo().hashCode();
                }
                if (sheet.getSheetName() != null) {
                    sheetNameHashCode = sheet.getSheetName().hashCode();
                }
            }
            return sheetNoHashCode ^ sheetNameHashCode;
        }

        private final WriteSheet sheet;

        private final Stack<CellRangeAddress> regions = new Stack<CellRangeAddress>();

        private SheetRegions(final WriteSheet sheet) {
            this.sheet = sheet;
        }

        @Override
        public int hashCode() {
            return hashCode(sheet);
        }
    }
}
