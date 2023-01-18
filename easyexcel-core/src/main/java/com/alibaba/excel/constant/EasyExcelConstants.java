package com.alibaba.excel.constant;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Used to store constant
 *
 * @author Jiaju Zhuang
 */
public class EasyExcelConstants {

    /**
     * Excel by default with 15 to store Numbers, and the double in Java can use to store number 17, led to the accuracy
     * will be a problem. So you need to set up 15 to deal with precision
     */
    public static final MathContext EXCEL_MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);

}
