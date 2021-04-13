package com.alibaba.excel.metadata;

import java.util.Date;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import lombok.Data;

/**
 * TODO
 *
 * @author Jiaju Zhuang
 */
@Data
public class WriteCellData<T> extends CellData<T> {
    /**
     * Support only when writing.{@link CellDataTypeEnum#DATE}
     */
    private Date dateValue;
    /**
     * {@link CellDataTypeEnum#IMAGE}
     */
    private ImageData imageDataValue;
    /**
     * rich text.{@link CellDataTypeEnum#RICH_TEXT_STRING}
     */
    private RichTextStringData richTextStringDataValue;

    /**
     * comment
     */
    private CommentData commentData;
    /**
     * hyper link
     */
    private HyperlinkData hyperlinkData;
    /**
     * sytle
     */
    private WriteCellStyle writeCellStyle;
}
