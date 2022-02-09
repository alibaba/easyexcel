package com.alibaba.excel.enums.poi;

import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;

/**
 * The enumeration value indicating the line style of a border in a cell,
 * i.e., whether it is bordered dash dot, dash dot dot, dashed, dotted, double, hair, medium,
 * medium dash dot, medium dash dot dot, medium dashed, none, slant dash dot, thick or thin.
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum BorderStyleEnum {
    /**
     * null
     */
    DEFAULT(null),

    /**
     * No border (default)
     */
    NONE(BorderStyle.NONE),

    /**
     * Thin border
     */
    THIN(BorderStyle.THIN),

    /**
     * Medium border
     */
    MEDIUM(BorderStyle.MEDIUM),

    /**
     * dash border
     */
    DASHED(BorderStyle.DASHED),

    /**
     * dot border
     */
    DOTTED(BorderStyle.DOTTED),

    /**
     * Thick border
     */
    THICK(BorderStyle.THICK),

    /**
     * double-line border
     */
    DOUBLE(BorderStyle.DOUBLE),

    /**
     * hair-line border
     */
    HAIR(BorderStyle.HAIR),

    /**
     * Medium dashed border
     */
    MEDIUM_DASHED(BorderStyle.MEDIUM_DASHED),

    /**
     * dash-dot border
     */
    DASH_DOT(BorderStyle.DASH_DOT),

    /**
     * medium dash-dot border
     */
    MEDIUM_DASH_DOT(BorderStyle.MEDIUM_DASH_DOT),

    /**
     * dash-dot-dot border
     */
    DASH_DOT_DOT(BorderStyle.DASH_DOT_DOT),

    /**
     * medium dash-dot-dot border
     */
    MEDIUM_DASH_DOT_DOT(BorderStyle.MEDIUM_DASH_DOT_DOT),

    /**
     * slanted dash-dot border
     */
    SLANTED_DASH_DOT(BorderStyle.SLANTED_DASH_DOT);

    BorderStyle poiBorderStyle;

    BorderStyleEnum(BorderStyle poiBorderStyle) {
        this.poiBorderStyle = poiBorderStyle;
    }
}
