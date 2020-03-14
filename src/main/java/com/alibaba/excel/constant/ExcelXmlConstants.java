package com.alibaba.excel.constant;

/**
 * @author jipengfei
 */
public class ExcelXmlConstants {
    public static final String DIMENSION = "dimension";
    public static final String DIMENSION_REF = "ref";
    public static final String POSITION = "r";

    public static final String ROW_TAG = "row";
    public static final String CELL_TAG = "c";
    public static final String CELL_VALUE_TYPE_TAG = "t";
    /**
     * Number formatted label
     */
    public static final String CELL_DATA_FORMAT_TAG = "s";
    public static final String CELL_FORMULA_TAG = "f";
    public static final String CELL_VALUE_TAG = "v";
    /**
     * When the data is "inlineStr" his tag is "t"
     */
    public static final String CELL_INLINE_STRING_VALUE_TAG = "t";

    public static final String MERGE_CELL_TAG = "mergeCell";
    public static final String HYPERLINK_TAG = "hyperlink";

    /**
     * Cell range split
     */
    public static final String CELL_RANGE_SPLIT = ":";
    /**
     * ref attribute
     */
    public static final String ATTRIBUTE_REF = "ref";
    /**
     * location attribute
     */
    public static final String ATTRIBUTE_LOCATION = "location";
}
