package com.alibaba.excel.constant;

/**
 * @author jipengfei
 */
public class ExcelXmlConstants {
    public static final String DIMENSION_TAG = "dimension";
    public static final String ROW_TAG = "row";
    public static final String CELL_FORMULA_TAG = "f";
    public static final String CELL_VALUE_TAG = "v";
    /**
     * When the data is "inlineStr" his tag is "t"
     */
    public static final String CELL_INLINE_STRING_VALUE_TAG = "t";
    public static final String CELL_TAG = "c";
    public static final String MERGE_CELL_TAG = "mergeCell";
    public static final String HYPERLINK_TAG = "hyperlink";

    public static final String X_DIMENSION_TAG = "x:dimension";
    public static final String X_ROW_TAG = "x:row";
    public static final String X_CELL_FORMULA_TAG = "x:f";
    public static final String X_CELL_VALUE_TAG = "x:v";
    /**
     * When the data is "inlineStr" his tag is "t"
     */
    public static final String X_CELL_INLINE_STRING_VALUE_TAG = "x:t";
    public static final String X_CELL_TAG = "x:c";
    public static final String X_MERGE_CELL_TAG = "x:mergeCell";
    public static final String X_HYPERLINK_TAG = "x:hyperlink";

    /**
     * s attribute
     */
    public static final String ATTRIBUTE_S = "s";
    /**
     * ref attribute
     */
    public static final String ATTRIBUTE_REF = "ref";
    /**
     * r attribute
     */
    public static final String ATTRIBUTE_R = "r";
    /**
     * t attribute
     */
    public static final String ATTRIBUTE_T = "t";
    /**
     * location attribute
     */
    public static final String ATTRIBUTE_LOCATION = "location";

    /**
     * rId attribute
     */
    public static final String ATTRIBUTE_RID = "r:id";

    /**
     * Cell range split
     */
    public static final String CELL_RANGE_SPLIT = ":";

    // The following is a constant read the `SharedStrings.xml`

    /**
     * text
     */
    public static final String SHAREDSTRINGS_T_TAG = "t";
    public static final String SHAREDSTRINGS_X_T_TAG = "x:t";

    /**
     * SharedStringItem
     */
    public static final String SHAREDSTRINGS_SI_TAG = "si";
    public static final String SHAREDSTRINGS_X_SI_TAG = "x:si";

    /**
     * Mac 2016 2017 will have this extra field to ignore
     */
    public static final String SHAREDSTRINGS_RPH_TAG = "rPh";
    public static final String SHAREDSTRINGS_X_RPH_TAG = "x:rPh";

}
