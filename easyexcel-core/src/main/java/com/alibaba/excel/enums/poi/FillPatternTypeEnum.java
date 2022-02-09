package com.alibaba.excel.enums.poi;

import lombok.Getter;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * The enumeration value indicating the style of fill pattern being used for a cell format.
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum FillPatternTypeEnum {

    /**
     * null
     */
    DEFAULT(null),

    /**
     * No background
     */
    NO_FILL(FillPatternType.NO_FILL),

    /**
     * Solidly filled
     */
    SOLID_FOREGROUND(FillPatternType.SOLID_FOREGROUND),

    /**
     * Small fine dots
     */
    FINE_DOTS(FillPatternType.FINE_DOTS),

    /**
     * Wide dots
     */
    ALT_BARS(FillPatternType.ALT_BARS),

    /**
     * Sparse dots
     */
    SPARSE_DOTS(FillPatternType.SPARSE_DOTS),

    /**
     * Thick horizontal bands
     */
    THICK_HORZ_BANDS(FillPatternType.THICK_HORZ_BANDS),

    /**
     * Thick vertical bands
     */
    THICK_VERT_BANDS(FillPatternType.THICK_VERT_BANDS),

    /**
     * Thick backward facing diagonals
     */
    THICK_BACKWARD_DIAG(FillPatternType.THICK_BACKWARD_DIAG),

    /**
     * Thick forward facing diagonals
     */
    THICK_FORWARD_DIAG(FillPatternType.THICK_FORWARD_DIAG),

    /**
     * Large spots
     */
    BIG_SPOTS(FillPatternType.BIG_SPOTS),

    /**
     * Brick-like layout
     */
    BRICKS(FillPatternType.BRICKS),

    /**
     * Thin horizontal bands
     */
    THIN_HORZ_BANDS(FillPatternType.THIN_HORZ_BANDS),

    /**
     * Thin vertical bands
     */
    THIN_VERT_BANDS(FillPatternType.THIN_VERT_BANDS),

    /**
     * Thin backward diagonal
     */
    THIN_BACKWARD_DIAG(FillPatternType.THIN_BACKWARD_DIAG),

    /**
     * Thin forward diagonal
     */
    THIN_FORWARD_DIAG(FillPatternType.THIN_FORWARD_DIAG),

    /**
     * Squares
     */
    SQUARES(FillPatternType.SQUARES),

    /**
     * Diamonds
     */
    DIAMONDS(FillPatternType.DIAMONDS),

    /**
     * Less Dots
     */
    LESS_DOTS(FillPatternType.LESS_DOTS),

    /**
     * Least Dots
     */
    LEAST_DOTS(FillPatternType.LEAST_DOTS);

    FillPatternType poiFillPatternType;

    FillPatternTypeEnum(FillPatternType poiFillPatternType) {
        this.poiFillPatternType = poiFillPatternType;
    }
}
