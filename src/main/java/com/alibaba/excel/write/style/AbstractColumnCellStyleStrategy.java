package com.alibaba.excel.write.style;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;

/**
 *
 * Use the same style for the column
 *
 * @author zhuangjiaju
 */
public abstract class AbstractColumnCellStyleStrategy extends AbstractCellStyleStrategy {

    private Workbook workbook;
    private Map<Integer, CellStyle> headCellStyleCache = new HashMap<Integer, CellStyle>();
    private Map<Integer, CellStyle> contentCellStyleCache = new HashMap<Integer, CellStyle>();

    @Override
    protected void initCellStyle(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, int relativeRowIndex) {
        int columnIndex = head.getColumnIndex();
        if (headCellStyleCache.containsKey(columnIndex)) {
            cell.setCellStyle(headCellStyleCache.get(columnIndex));
        }
        CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headCellStyle(head));
        headCellStyleCache.put(columnIndex, cellStyle);
        cell.setCellStyle(cellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, int relativeRowIndex) {
        int columnIndex = head.getColumnIndex();
        if (contentCellStyleCache.containsKey(columnIndex)) {
            cell.setCellStyle(contentCellStyleCache.get(columnIndex));
        }
        CellStyle cellStyle = StyleUtil.buildContentCellStyle(workbook, contentCellStyle(head));
        contentCellStyleCache.put(columnIndex, cellStyle);
        cell.setCellStyle(cellStyle);
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head
     * @return
     */
    protected abstract com.alibaba.excel.metadata.CellStyle headCellStyle(Head head);

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head
     * @return
     */
    protected abstract com.alibaba.excel.metadata.CellStyle contentCellStyle(Head head);

}
