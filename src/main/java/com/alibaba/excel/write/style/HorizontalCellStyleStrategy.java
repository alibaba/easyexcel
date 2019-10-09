package com.alibaba.excel.write.style;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

/**
 *
 * Use the same style for the row
 *
 * @author Jiaju Zhuang
 */
public class HorizontalCellStyleStrategy extends AbstractCellStyleStrategy {

    private WriteCellStyle headWriteCellStyle;
    private List<WriteCellStyle> contentWriteCellStyleList;

    private CellStyle headCellStyle;
    private List<CellStyle> contentCellStyleList;

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
        List<WriteCellStyle> contentWriteCellStyleList) {
        this.headWriteCellStyle = headWriteCellStyle;
        this.contentWriteCellStyleList = contentWriteCellStyleList;
    }

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        this.headWriteCellStyle = headWriteCellStyle;
        contentWriteCellStyleList = new ArrayList<WriteCellStyle>();
        contentWriteCellStyleList.add(contentWriteCellStyle);
    }

    @Override
    protected void initCellStyle(Workbook workbook) {
        if (headWriteCellStyle != null) {
            headCellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
        }
        if (contentWriteCellStyleList != null && !contentWriteCellStyleList.isEmpty()) {
            contentCellStyleList = new ArrayList<CellStyle>();
            for (WriteCellStyle writeCellStyle : contentWriteCellStyleList) {
                contentCellStyleList.add(StyleUtil.buildContentCellStyle(workbook, writeCellStyle));
            }
        }
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (headCellStyle == null) {
            return;
        }
        cell.setCellStyle(headCellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (contentCellStyleList == null || contentCellStyleList.isEmpty()) {
            return;
        }
        cell.setCellStyle(contentCellStyleList.get(relativeRowIndex % contentCellStyleList.size()));
    }

}
