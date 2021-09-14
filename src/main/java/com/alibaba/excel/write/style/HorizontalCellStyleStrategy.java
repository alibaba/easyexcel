package com.alibaba.excel.write.style;

import java.util.List;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Use the same style for the row
 *
 * @author Jiaju Zhuang
 */
public class HorizontalCellStyleStrategy extends AbstractVerticalCellStyleStrategy {

    private WriteCellStyle headWriteCellStyle;
    private List<WriteCellStyle> contentWriteCellStyleList;

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
        List<WriteCellStyle> contentWriteCellStyleList) {
        this.headWriteCellStyle = headWriteCellStyle;
        this.contentWriteCellStyleList = contentWriteCellStyleList;
    }

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        this.headWriteCellStyle = headWriteCellStyle;
        if (contentWriteCellStyle != null) {
            this.contentWriteCellStyleList = ListUtils.newArrayList(contentWriteCellStyle);
        }
    }

    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        return headWriteCellStyle;
    }

    @Override
    protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
        if (CollectionUtils.isEmpty(contentWriteCellStyleList)) {
            return null;
        }
        return contentWriteCellStyleList.get(context.getRelativeRowIndex() % contentWriteCellStyleList.size());
    }

}
