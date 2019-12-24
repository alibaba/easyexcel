package com.alibaba.excel.write.builder;

import java.util.ArrayList;
import java.util.Collection;

import com.alibaba.excel.metadata.AbstractParameterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteBasicParameter;

/**
 * Build ExcelBuilder
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractExcelWriterParameterBuilder<T extends AbstractExcelWriterParameterBuilder,
    C extends WriteBasicParameter> extends AbstractParameterBuilder<T, C> {
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param relativeHeadRowIndex
     * @return
     */
    public T relativeHeadRowIndex(Integer relativeHeadRowIndex) {
        parameter().setRelativeHeadRowIndex(relativeHeadRowIndex);
        return self();
    }

    /**
     * Need Head
     */
    public T needHead(Boolean needHead) {
        parameter().setNeedHead(needHead);
        return self();
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public T registerWriteHandler(WriteHandler writeHandler) {
        if (parameter().getCustomWriteHandlerList() == null) {
            parameter().setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        parameter().getCustomWriteHandlerList().add(writeHandler);
        return self();
    }

    /**
     * Use the default style.Default is true.
     *
     * @param useDefaultStyle
     * @return
     */
    public T useDefaultStyle(Boolean useDefaultStyle) {
        parameter().setUseDefaultStyle(useDefaultStyle);
        return self();
    }

    /**
     * Whether to automatically merge headers.Default is true.
     *
     * @param automaticMergeHead
     * @return
     */
    public T automaticMergeHead(Boolean automaticMergeHead) {
        parameter().setAutomaticMergeHead(automaticMergeHead);
        return self();
    }

    /**
     * Ignore the custom columns.
     */
    public T excludeColumnIndexes(Collection<Integer> excludeColumnIndexes) {
        parameter().setExcludeColumnIndexes(excludeColumnIndexes);
        return self();
    }

    /**
     * Ignore the custom columns.
     */
    public T excludeColumnFiledNames(Collection<String> excludeColumnFiledNames) {
        parameter().setExcludeColumnFiledNames(excludeColumnFiledNames);
        return self();
    }

    /**
     * Only output the custom columns.
     */
    public T includeColumnIndexes(Collection<Integer> includeColumnIndexes) {
        parameter().setIncludeColumnIndexes(includeColumnIndexes);
        return self();
    }

    /**
     * Only output the custom columns.
     */
    public T includeColumnFiledNames(Collection<String> includeColumnFiledNames) {
        parameter().setIncludeColumnFiledNames(includeColumnFiledNames);
        return self();
    }

}
