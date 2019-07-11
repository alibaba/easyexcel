package com.alibaba.excel.write.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;

/**
 *
 * Use the same style for the row
 *
 * @author zhuangjiaju
 */
public class RowCellStyleStrategy extends AbstractCellStyleStrategy {

    private CellStyle headCellStyle;
    private List<CellStyle> contentCellStyleList;

    private org.apache.poi.ss.usermodel.CellStyle poiHeadCellStyle;
    private List<org.apache.poi.ss.usermodel.CellStyle> poiContentCellStyleList;

    public RowCellStyleStrategy(CellStyle headCellStyle, List<CellStyle> contentCellStyleList) {
        this.headCellStyle = headCellStyle;
        this.contentCellStyleList = contentCellStyleList;
    }

    public RowCellStyleStrategy(CellStyle headCellStyle, CellStyle contentCellStyle) {
        this.headCellStyle = headCellStyle;
        contentCellStyleList = new ArrayList<CellStyle>();
        contentCellStyleList.add(contentCellStyle);
    }

    @Override
    protected void initCellStyle(Workbook workbook) {
        if (headCellStyle != null) {
            poiHeadCellStyle = StyleUtil.buildCellStyle(workbook, headCellStyle);
        }
        if (contentCellStyleList != null && !contentCellStyleList.isEmpty()) {
            poiContentCellStyleList = new ArrayList<org.apache.poi.ss.usermodel.CellStyle>();
            for (CellStyle cellStyle : contentCellStyleList) {
                poiContentCellStyleList.add(StyleUtil.buildCellStyle(workbook, cellStyle));
            }
        }
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, int relativeRowIndex) {
        if (poiHeadCellStyle == null) {
            return;
        }
        cell.setCellStyle(poiHeadCellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, int relativeRowIndex) {
        if (poiContentCellStyleList == null || poiContentCellStyleList.isEmpty()) {
            return;
        }
        cell.setCellStyle(poiContentCellStyleList.get(relativeRowIndex % poiContentCellStyleList.size()));
    }

}
