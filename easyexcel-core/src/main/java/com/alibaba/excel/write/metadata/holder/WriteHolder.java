package com.alibaba.excel.write.metadata.holder;

import java.util.Collection;

import com.alibaba.excel.metadata.ConfigurationHolder;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;

/**
 * Get the corresponding Holder
 *
 * @author Jiaju Zhuang
 **/
public interface WriteHolder extends ConfigurationHolder {
    /**
     * What 'ExcelWriteHeadProperty' does the currently operated cell need to execute
     *
     * @return
     */
    ExcelWriteHeadProperty excelWriteHeadProperty();

    /**
     * Is to determine if a field needs to be ignored
     *
     * @param fieldName
     * @param columnIndex
     * @return
     */
    boolean ignore(String fieldName, Integer columnIndex);

    /**
     * Whether a header is required for the currently operated cell
     *
     * @return
     */
    boolean needHead();

    /**
     * Whether need automatic merge headers.
     *
     * @return
     */
    boolean automaticMergeHead();

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @return
     */
    int relativeHeadRowIndex();

    /**
     * Data will be order by  {@link #includeColumnFieldNames} or  {@link #includeColumnIndexes}.
     *
     * default is false.
     *
     * @return
     */

    boolean orderByIncludeColumn();

    /**
     * Only output the custom columns.
     *
     * @return
     */
    Collection<Integer> includeColumnIndexes();

    /**
     * Only output the custom columns.
     *
     * @return
     */
    Collection<String> includeColumnFieldNames();

    /**
     * Ignore the custom columns.
     *
     * @return
     */
    Collection<Integer> excludeColumnIndexes();

    /**
     * Ignore the custom columns.
     *
     * @return
     */
    Collection<String> excludeColumnFieldNames();
}
