package com.alibaba.excel.write.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.excel.metadata.BasicParameter;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Write basic parameter
 *
 * @author Jiaju Zhuang
 **/
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
    private Collection<String> excludeColumnFiledNames;
    /**
     * Only output the custom columns.
     */
    private Collection<Integer> includeColumnIndexes;
    /**
     * Only output the custom columns.
     */
    private Collection<String> includeColumnFiledNames;

    public Integer getRelativeHeadRowIndex() {
        return relativeHeadRowIndex;
    }

    public void setRelativeHeadRowIndex(Integer relativeHeadRowIndex) {
        this.relativeHeadRowIndex = relativeHeadRowIndex;
    }

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }

    public List<WriteHandler> getCustomWriteHandlerList() {
        return customWriteHandlerList;
    }

    public void setCustomWriteHandlerList(List<WriteHandler> customWriteHandlerList) {
        this.customWriteHandlerList = customWriteHandlerList;
    }

    public Boolean getUseDefaultStyle() {
        return useDefaultStyle;
    }

    public void setUseDefaultStyle(Boolean useDefaultStyle) {
        this.useDefaultStyle = useDefaultStyle;
    }

    public Boolean getAutomaticMergeHead() {
        return automaticMergeHead;
    }

    public void setAutomaticMergeHead(Boolean automaticMergeHead) {
        this.automaticMergeHead = automaticMergeHead;
    }

    public Collection<Integer> getExcludeColumnIndexes() {
        return excludeColumnIndexes;
    }

    public void setExcludeColumnIndexes(Collection<Integer> excludeColumnIndexes) {
        this.excludeColumnIndexes = excludeColumnIndexes;
    }

    public Collection<String> getExcludeColumnFiledNames() {
        return excludeColumnFiledNames;
    }

    public void setExcludeColumnFiledNames(Collection<String> excludeColumnFiledNames) {
        this.excludeColumnFiledNames = excludeColumnFiledNames;
    }

    public Collection<Integer> getIncludeColumnIndexes() {
        return includeColumnIndexes;
    }

    public void setIncludeColumnIndexes(Collection<Integer> includeColumnIndexes) {
        this.includeColumnIndexes = includeColumnIndexes;
    }

    public Collection<String> getIncludeColumnFiledNames() {
        return includeColumnFiledNames;
    }

    public void setIncludeColumnFiledNames(Collection<String> includeColumnFiledNames) {
        this.includeColumnFiledNames = includeColumnFiledNames;
    }

}
