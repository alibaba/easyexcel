package com.alibaba.excel.write.style;

import java.util.Map;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Use the same style for the column
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractVerticalCellStyleStrategy extends AbstractCellStyleStrategy {

    private Workbook workbook;
    private final Map<Integer, Map<Integer, Map<Integer, CellStyle>>> headCellStyleCache = MapUtils.newHashMap();
    private final Map<Integer, Map<Integer, Map<Integer, CellStyle>>> contentCellStyleCache = MapUtils.newHashMap();

    @Override
    protected void initCellStyle(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    protected void setHeadCellStyle(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, Integer relativeRowIndex) {
        if (head == null) {
            return;
        }
        Map<Integer, CellStyle> styleMap = getStyleMap(headCellStyleCache, writeSheetHolder, writeTableHolder);

        int columnIndex = head.getColumnIndex();
        if (styleMap.containsKey(columnIndex)) {
            CellStyle cellStyle = styleMap.get(columnIndex);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            return;
        }
        WriteCellStyle headCellStyle = headCellStyle(head);
        if (headCellStyle == null) {
            styleMap.put(columnIndex, StyleUtil.buildHeadCellStyle(workbook, null));
        } else {
            CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headCellStyle);
            styleMap.put(columnIndex, cellStyle);
            cell.setCellStyle(cellStyle);
        }
    }

    @Override
    protected void setContentCellStyle(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, Integer relativeRowIndex) {
        if (head == null) {
            return;
        }
        Map<Integer, CellStyle> styleMap = getStyleMap(contentCellStyleCache, writeSheetHolder, writeTableHolder);

        int columnIndex = head.getColumnIndex();
        if (styleMap.containsKey(columnIndex)) {
            CellStyle cellStyle = styleMap.get(columnIndex);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            return;
        }
        WriteCellStyle contentCellStyle = contentCellStyle(cell, head, relativeRowIndex);
        if (contentCellStyle == null) {
            styleMap.put(columnIndex, StyleUtil.buildContentCellStyle(workbook, null));
        } else {
            CellStyle cellStyle = StyleUtil.buildContentCellStyle(workbook, contentCellStyle);
            styleMap.put(columnIndex, cellStyle);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @return
     */
    protected WriteCellStyle contentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        return contentCellStyle(head);
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected abstract WriteCellStyle headCellStyle(Head head);

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected WriteCellStyle contentCellStyle(Head head) {
        throw new UnsupportedOperationException(
            "One of the two methods 'contentCellStyle(Cell cell, Head head, Integer relativeRowIndex)' and "
                + "'contentCellStyle(Head head)' must be implemented.");
    }

    private Map<Integer, CellStyle> getStyleMap(Map<Integer, Map<Integer, Map<Integer, CellStyle>>> cellStyleCache,
        WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder) {
        Map<Integer, Map<Integer, CellStyle>> tableStyleMap = cellStyleCache.computeIfAbsent(
            writeSheetHolder.getSheetNo(), key -> MapUtils.newHashMap());
        Integer tableNo = 0;
        if (writeTableHolder != null) {
            tableNo = writeTableHolder.getTableNo();
        }
        return tableStyleMap.computeIfAbsent(tableNo, key -> MapUtils.newHashMap());
    }

}
