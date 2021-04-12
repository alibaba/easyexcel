package com.alibaba.excel.write.style;

import java.util.List;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Use the same style for the row
 *
 * @author Jiaju Zhuang
 */
public class HorizontalCellStyleStrategy extends AbstractVerticalCellStyleStrategy {

    private final WriteCellStyle headWriteCellStyle;
    private final List<WriteCellStyle> contentWriteCellStyleList;

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
        List<WriteCellStyle> contentWriteCellStyleList) {
        this.headWriteCellStyle = headWriteCellStyle;
        this.contentWriteCellStyleList = contentWriteCellStyleList;
    }

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        this.headWriteCellStyle = headWriteCellStyle;
        contentWriteCellStyleList = ListUtils.newArrayList();
        contentWriteCellStyleList.add(contentWriteCellStyle);
    }

    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        return headWriteCellStyle;
    }

    @Override
    protected WriteCellStyle contentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (CollectionUtils.isEmpty(contentWriteCellStyleList)) {
            return null;
        }
        return contentWriteCellStyleList.get(relativeRowIndex % contentWriteCellStyleList.size());
    }

}
