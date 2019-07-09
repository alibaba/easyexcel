package com.alibaba.excel.write.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;

public class HorizontalCellStyleStrategy extends AbstractCellStyleStrategy {

    private CellStyle headCellStyle;
    private List<CellStyle> contentCellStyleList;

    private org.apache.poi.ss.usermodel.CellStyle poiHeadCellStyle;
    private List<org.apache.poi.ss.usermodel.CellStyle> poiContentCellStyleList;

    public HorizontalCellStyleStrategy(CellStyle headCellStyle, List<CellStyle> contentCellStyleList) {
        if (headCellStyle == null || contentCellStyleList == null || contentCellStyleList.isEmpty()) {
            throw new IllegalArgumentException("All parameters can not be null");
        }
        this.headCellStyle = headCellStyle;
        this.contentCellStyleList = contentCellStyleList;
    }

    public HorizontalCellStyleStrategy(CellStyle headCellStyle, CellStyle contentCellStyle) {
        if (headCellStyle == null || contentCellStyle == null) {
            throw new IllegalArgumentException("All parameters can not be null");
        }
        this.headCellStyle = headCellStyle;
        contentCellStyleList = new ArrayList<CellStyle>();
        contentCellStyleList.add(contentCellStyle);
    }

    @Override
    protected void initCellStyle(Workbook workbook) {
        poiHeadCellStyle = StyleUtil.buildCellStyle(workbook, headCellStyle);
        poiContentCellStyleList = new ArrayList<org.apache.poi.ss.usermodel.CellStyle>();
        for (CellStyle cellStyle : contentCellStyleList) {
            poiContentCellStyleList.add(StyleUtil.buildCellStyle(workbook, cellStyle));
        }
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, int relativeRowIndex) {
        cell.setCellStyle(poiHeadCellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, int relativeRowIndex) {
        cell.setCellStyle(poiContentCellStyleList.get(relativeRowIndex % poiContentCellStyleList.size()));
    }

}
