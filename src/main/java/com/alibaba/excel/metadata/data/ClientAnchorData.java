package com.alibaba.excel.metadata.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.util.Internal;

/**
 * A client anchor is attached to an excel worksheet.  It anchors against
 * absolute coordinates, a top-left cell and fixed height and width, or
 * a top-left and bottom-right cell, depending on the {@link ClientAnchorData.AnchorType}:
 * <ol>
 * <li> {@link ClientAnchor.AnchorType#DONT_MOVE_AND_RESIZE} == absolute top-left coordinates and width/height, no
 * cell references
 * <li> {@link ClientAnchor.AnchorType#MOVE_DONT_RESIZE} == fixed top-left cell reference, absolute width/height
 * <li> {@link ClientAnchor.AnchorType#MOVE_AND_RESIZE} == fixed top-left and bottom-right cell references, dynamic
 * width/height
 * </ol>
 * Note this class only reports the current values for possibly calculated positions and sizes.
 * If the sheet row/column sizes or positions shift, this needs updating via external calculations.
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class ClientAnchorData extends CoordinateData {
    /**
     * top
     */
    private Integer top;

    /**
     * right
     */
    private Integer right;

    /**
     * bottom
     */
    private Integer bottom;

    /**
     * left
     */
    private Integer left;

    /**
     * anchor type
     */
    private AnchorType anchorType;

    @Getter
    public enum AnchorType {
        /**
         * Move and Resize With Anchor Cells (0)
         * <p>
         * Specifies that the current drawing shall move and
         * resize to maintain its row and column anchors (i.e. the
         * object is anchored to the actual from and to row and column)
         * </p>
         */
        MOVE_AND_RESIZE(ClientAnchor.AnchorType.MOVE_AND_RESIZE),

        /**
         * Don't Move but do Resize With Anchor Cells (1)
         * <p>
         * Specifies that the current drawing shall not move with its
         * row and column, but should be resized. This option is not normally
         * used, but is included for completeness.
         * </p>
         * Note: Excel has no setting for this combination, nor does the ECMA standard.
         */
        DONT_MOVE_DO_RESIZE(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE),

        /**
         * Move With Cells but Do Not Resize (2)
         * <p>
         * Specifies that the current drawing shall move with its
         * row and column (i.e. the object is anchored to the
         * actual from row and column), but that the size shall remain absolute.
         * </p>
         * <p>
         * If additional rows/columns are added between the from and to locations of the drawing,
         * the drawing shall move its to anchors as needed to maintain this same absolute size.
         * </p>
         */
        MOVE_DONT_RESIZE(ClientAnchor.AnchorType.MOVE_DONT_RESIZE),

        /**
         * Do Not Move or Resize With Underlying Rows/Columns (3)
         * <p>
         * Specifies that the current start and end positions shall
         * be maintained with respect to the distances from the
         * absolute start point of the worksheet.
         * </p>
         * <p>
         * If additional rows/columns are added before the
         * drawing, the drawing shall move its anchors as needed
         * to maintain this same absolute position.
         * </p>
         */
        DONT_MOVE_AND_RESIZE(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

        ClientAnchor.AnchorType value;

        AnchorType(ClientAnchor.AnchorType value) {
            this.value = value;
        }

        /**
         * return the AnchorType corresponding to the code
         *
         * @param value the anchor type code
         * @return the anchor type enum
         */
        @Internal
        public static ClientAnchorData.AnchorType byId(int value) {
            return values()[value];
        }
    }
}
