package com.alibaba.excel.metadata.data;

import lombok.Data;

/**
 * comment
 *
 * @author Jiaju Zhuang
 */
@Data
public class CommentData {
    /**
     * Row index
     */
    private Integer rowIndex;
    /**
     * Column index
     */
    private Integer columnIndex;
    /**
     * Name of the original comment author
     */
    private String author;
    /**
     * rich text string
     */
    private RichTextStringData richTextStringData;
}
