package com.alibaba.excel.write.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.excel.metadata.BasicParameter;
import com.alibaba.excel.write.handler.WriteHandler;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Write basic parameter
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class WriteBasicParameter extends BasicParameter {
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer relativeHeadRowIndex;
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Custom type handler override the default
     */
    private List<WriteHandler> customWriteHandlerList = new ArrayList<WriteHandler>();
    /**
     * Use the default style.Default is true.
     */
    private Boolean useDefaultStyle;
    /**
     * Whether to automatically merge headers.Default is true.
     */
    private Boolean automaticMergeHead;
    /**
     * Ignore the custom columns.
     */
    private Collection<Integer> excludeColumnIndexes;
    /**
     * Ignore the custom columns.
     */
    private Collection<String> excludeColumnFieldNames;
    /**
     * Only output the custom columns.
     */
    private Collection<Integer> includeColumnIndexes;
    /**
     * Only output the custom columns.
     */
    private Collection<String> includeColumnFieldNames;

    /**
     * Data will be order by  {@link #includeColumnFieldNames} or  {@link #includeColumnIndexes}.
     *
     * default is false.
     */
    private Boolean orderByIncludeColumn;
}
